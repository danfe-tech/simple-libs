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

/**
 *
 * If you want the objects that are created by JdbcTemplate using a
 * {@link com.github.simplejdbc.mapper.RowMapper} to hold a reference to the
 * JdbcTemplate object that created them, you can make that type of objects to
 * implement this interface.
 *
 * @author Suraj Chhetry
 */
public interface JdbcAware {

    public void setJdbcHelper(JdbcTemplate jdbc);
}
