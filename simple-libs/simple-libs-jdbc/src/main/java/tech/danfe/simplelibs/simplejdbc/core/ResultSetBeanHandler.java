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
import tech.danfe.simplelibs.simplejdbc.mapper.RowMapper;

/**
 *
 * @author Suraj Chhetry
 * @param <T>
 */
public abstract class ResultSetBeanHandler<T> extends ResultSetHandler {

    private RowMapper<T> mapper;

    public ResultSetBeanHandler(RowMapper<T> mapper) {
        this.mapper = mapper;
    }

    public ResultSetBeanHandler(int fetchSize, RowMapper<T> mapper) {
        super(fetchSize);
        this.mapper = mapper;
    }

    public ResultSetBeanHandler(int fetchSize, int maxRows, RowMapper<T> mapper) {
        super(fetchSize, maxRows);
        this.mapper = mapper;
    }

    public ResultSetBeanHandler(int fetchSize, int maxRows, int timeOut, RowMapper<T> c) {
        super(fetchSize, maxRows, timeOut);
        this.mapper = c;
    }

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        processHandler(mapper.mapRow(rs));
    }

    public abstract void processHandler(T bean);
}
