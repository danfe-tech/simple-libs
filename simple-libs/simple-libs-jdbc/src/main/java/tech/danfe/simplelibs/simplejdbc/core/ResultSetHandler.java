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

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Suraj Chhetry
 */
public abstract class ResultSetHandler {

    /**
     * Creates a ResultSetHandler with default fetchsize, maxRows and timeOut
     * parameters of the underlying Jdbc driver
     */
    public ResultSetHandler() {

    }

    /**
     * Creates a new ResultSetHandler with the specified resultset fetch size.
     * The fetch size parameter is important for selecting a large amount of
     * rows from the database. If not set, you may see OutOfMemory errors.
     *
     * @param fetchSize ResultSet fetch size
     */
    public ResultSetHandler(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public ResultSetHandler(int fetchSize, int maxRows) {
        this.fetchSize = fetchSize;
        this.maxRows = maxRows;
    }

    public ResultSetHandler(int fetchSize, int maxRows, int timeOut) {
        this.fetchSize = fetchSize;
        this.maxRows = maxRows;
        this.timeOut = timeOut;
    }

    int fetchSize;
    int maxRows;
    int timeOut;
    int rowNo;

    public int getRowNo() {
        return rowNo;
    }

    /**
     * Should do whatever is needed for the row. The next() method of the
     * resultset is called prior to calling this method so you should never call
     * the next() method within the processRow method unless you know what you
     * are doing.
     *
     * @param rs Fetched resultSet
     * @throws SQLException Thrown in case of a db error
     */
    public abstract void processRow(ResultSet rs) throws SQLException;
}
