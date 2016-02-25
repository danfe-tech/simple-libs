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
import java.util.List;

/**
 *
 * @author Suraj Chhetry
 */
public class BatchStatement implements AutoCloseable, BatchOperation {

    private final Connection connection;
    private String sql;
    private final List<BatchParameter> parameters;
    private PreparedStatement statement = null;

    public BatchStatement(Connection connection, String sql, List<BatchParameter> parameters) {
        this.connection = connection;
        this.sql = sql;
        this.parameters = parameters;
        this.fillParameter();
    }

    @Override
    public void close() {
        try {
            this.statement.close();
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

    private void prepareSqlStatement() {
        for (BatchParameter bp : this.parameters) {
            for (int index = 0; index < bp.size(); index++) {
                this.sql = sql.replace(":" + bp.get(index).getName(), "?");
            }
        }
    }

    private void fillParameter() {
        try {
            this.prepareSqlStatement();
            statement = connection.prepareStatement(this.sql);
            if (!this.connection.getAutoCommit()) {
                this.connection.setAutoCommit(false);
            }
            for (BatchParameter bp : this.parameters) {
                for (int index = 1; index < bp.size(); index++) {
                    QueryParameter parameter = bp.get(index - 1);
                    if (parameter.getType() == QueryParameter.ParameterType.Object) {
                        statement.setObject(index, parameter.getValue());
                    }
                    if (parameter.getType() == QueryParameter.ParameterType.Date) {
                        statement.setDate(index, new java.sql.Date(((java.util.Date) parameter.getValue()).getTime()));
                    }
                }
                statement.addBatch();
            }

        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        }
    }
    
    @Override
    public int[] executeBatch() {
        try {
            int[] executeBatchResult = this.statement.executeBatch();
            this.connection.commit();
            return executeBatchResult;
        } catch (SQLException ex) {
            throw new DataAccessException(ex);
        }
    }

}
