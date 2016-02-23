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
package tech.danfe.simplelibs.simplejdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;

/**
 *
 * @author Suraj Chhetry
 *
 * ResultSetMappers are used to create map entries from result set. The executed
 * select query should return 2 fields for this to work as expected. If more
 * than 2 fields are returned, only first two fields are used.
 *
 * @param <K> Key type
 * @param <V> Value type
 */

public interface ResultSetMapper<K, V> {

    public AbstractMap.SimpleEntry<K, V> mapRow(ResultSet rs) throws SQLException;
}
