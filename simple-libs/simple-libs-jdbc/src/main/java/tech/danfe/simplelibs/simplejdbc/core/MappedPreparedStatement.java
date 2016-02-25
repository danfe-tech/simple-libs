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
import java.util.List;

/**
 * <pre>
 * This class converts {@code Map } based parameter to {@code  PreparedStatement}
 * </pre>
 *
 * @author Suraj Chhetry
 */
public class MappedPreparedStatement implements AutoCloseable {

    private final Connection connection;
    private String sql;
    private final List<QueryParameter> parameters;
    private PreparedStatement statement = null;

    /**
     * *
     *
     * @param connection
     * @param sql
     * @param parameters
     */
    public MappedPreparedStatement(Connection connection, String sql, List<QueryParameter> parameters) {
        this.connection = connection;
        this.sql = sql;
        this.parameters = parameters;
        this.fillParameter();
    }

    private void fillParameter() {
        try {
            for (int index = 0; index < parameters.size(); index++) {
                this.sql = sql.replace("\\b:" + parameters.get(index).getName() + "\\b", "?");
            }
            statement = connection.prepareStatement(this.sql);
            for (int index = 1; index <= parameters.size(); index++) {
                QueryParameter parameter = parameters.get(index - 1);

                if (parameter.getValue() == null) {
                    statement.setObject(index, parameter.getValue());
                    continue;
                }
                if (parameter.getType() == QueryParameter.ParameterType.Object) {
                    statement.setObject(index, parameter.getValue());
                }
                if (parameter.getType() == QueryParameter.ParameterType.Date) {
                    if (parameter.getValue() != null) {
                        statement.setDate(index, new java.sql.Date(((java.util.Date) parameter.getValue()).getTime()));
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
            return statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    public ResultSet executeQuery() {
        try {
            return this.statement.executeQuery();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

}
