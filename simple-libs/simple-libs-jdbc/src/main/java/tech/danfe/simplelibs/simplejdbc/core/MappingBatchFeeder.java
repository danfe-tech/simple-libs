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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**
 *
 * @author Suraj Chhetry
 * @param <T>
 */
public class MappingBatchFeeder<T> extends IteratorBatchFeeder<T> {

    StatementMapper<T> mapper;

    public MappingBatchFeeder(Iterator<T> i, StatementMapper<T> m) {
        super(i);
        mapper = m;
    }

    @Override
    public void feedStatement(PreparedStatement stmt, T object) throws SQLException {
        mapper.mapStatement(stmt, object);
    }
}
