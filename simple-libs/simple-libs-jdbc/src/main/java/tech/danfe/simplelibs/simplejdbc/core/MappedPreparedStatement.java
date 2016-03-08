/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.danfe.simplelibs.simplejdbc.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <pre>
 * This class converts {@code Map } based parameter to {@code  PreparedStatement}
 * </pre>
 *
 * @author Suraj Chhetry
 */
public class MappedPreparedStatement implements AutoCloseable {

    private final Connection connection;
    private final String sql;
    private final QueryParameterCollection parameters;
    private PreparedStatement statement = null;
    private static final Logger LOG = Logger.getLogger(MappedPreparedStatement.class.getName());

    /**
     * *
     *
     * @param connection
     * @param sql
     * @param parameters
     */
    public MappedPreparedStatement(Connection connection, String sql, QueryParameterCollection parameters) {
        this.connection = connection;
        this.sql = sql;
        this.parameters = parameters;
        this.fillParameter();
    }

    private void fillParameter() {
        try {
            NamedSqlParseResult parseResult = NamedStatementParserUtils.parseNamedSql(this.sql);
            statement = connection.prepareStatement(parseResult.getParsedSql());
            LOG.log(Level.INFO, " SQL :: {0}", parseResult.getParsedSql());
            LOG.log(Level.INFO, " TOTAL PARAMETER SIZE :: {0}", parseResult.getParaMap().size());
            for (int index = 1; index <= parseResult.getParaMap().size(); index++) {
                QueryParameter parameter = parameters.getParameter().get(index);
                LOG.log(Level.INFO, " Index  = {0} Value = {1}", new Object[]{index, parameter.toString()});
                if (parameter.getValue() == null) {
                    statement.setNull(index, Types.NULL);
                } else {
                    if (parameter.getType() == QueryParameter.ParameterType.Object) {
                        LOG.info("Setting Object Value");
                        statement.setObject(index, parameter.getValue());
                    }
                    if (parameter.getType() == QueryParameter.ParameterType.Boolean) {
                        LOG.info("Setting Boolean Value");
                        statement.setBoolean(index, Boolean.valueOf(parameter.getValue().toString()));
                    }
                    if (parameter.getType() == QueryParameter.ParameterType.Date) {
                        LOG.info("Setting Date Value");
                        if (parameter.getValue() != null) {
                            long javaTime = ((java.util.Date) parameter.getValue()).getTime();
                            statement.setDate(index, new java.sql.Date(javaTime));
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }

    // functions
    @Override
    public void close() {
        try {
            this.statement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    public int executeUpdate() {
        try {
            LOG.log(Level.INFO, "Executing SQL :: {0}", this.statement.toString());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    public ResultSet executeQuery() {
        try {
            LOG.log(Level.INFO, "Executing SQL :: {0}", this.statement.toString());
            return this.statement.executeQuery();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

}
