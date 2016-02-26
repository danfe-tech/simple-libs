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
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Suraj Chhetry
 */
public class BatchStatement implements AutoCloseable, BatchOperation {

    private final Connection connection;
    private final String sql;
    private final List<BatchParameter> parameters;
    private PreparedStatement statement = null;
    private static final Logger LOG = Logger.getLogger(BatchStatement.class.getName());

    public BatchStatement(Connection connection, String sql, List<BatchParameter> parameters) {
        this.connection = connection;
        this.sql = sql;
        this.parameters = parameters;
    }

    @Override
    public void close() {
        try {
            this.statement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    private void fillParameter(BatchParameter bp) {
        try {
            int currentIndex = 1;
            for (int index = 0; index < bp.size(); index++) {
                currentIndex = index + 1;
                QueryParameter parameter = bp.get(index);
                if (parameter.getValue() == null) {
                    statement.setNull(currentIndex, Types.NULL);

                } else {
                    if (parameter.getType() == QueryParameter.ParameterType.Object) {
                        statement.setObject(currentIndex, parameter.getValue());
                    }
                    if (parameter.getType() == QueryParameter.ParameterType.Date) {
                        statement.setDate(currentIndex, new java.sql.Date(((java.util.Date) parameter.getValue()).getTime()));
                    }
                }
            }

        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }

    @Override
    public int[] executeBatch() {
        try {
            statement = connection.prepareStatement(NamedStatementParserUtils.parseNamedSql(this.sql));
            if (!this.connection.getAutoCommit()) {
                this.connection.setAutoCommit(false);
            }
            for (BatchParameter bp : this.parameters) {
                this.fillParameter(bp);
                statement.addBatch();
            }
            int[] executeBatchResult = this.statement.executeBatch();
            this.connection.commit();
            this.connection.setAutoCommit(true);
            return executeBatchResult;
        } catch (SQLException ex) {
            try {
                this.connection.rollback();
                throw new DataAccessException(ex);
            } catch (SQLException ex1) {
                throw new DataAccessException(ex);
            }
        }
    }

    @Override
    public long executeBatch(int batchSize) {
        long total = 0;
        try {
            int[] executeBatchResult = null;
            statement = connection.prepareStatement(NamedStatementParserUtils.parseNamedSql(this.sql));
            if (!this.connection.getAutoCommit()) {
                this.connection.setAutoCommit(false);
            }
            int counter = 0;
            for (BatchParameter bp : this.parameters) {
                counter++;
                this.fillParameter(bp);
                statement.addBatch();
                if (counter % batchSize == 0) {
                    executeBatchResult = this.statement.executeBatch();
                    total += executeBatchResult.length;
                    this.connection.commit();
                }
            }
            //check remaining or not
            if (total < counter) {
                //execute remaining
                executeBatchResult = this.statement.executeBatch();
                total += executeBatchResult.length;
                this.connection.commit();
            }
            this.connection.setAutoCommit(true);
            return total;
        } catch (SQLException ex) {
            try {
                this.connection.rollback();
                throw new DataAccessException(ex);
            } catch (SQLException ex1) {
                throw new DataAccessException(ex);
            }
        }
    }

}
