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
package tech.danfe.simplelibs.simplejdbc;

import java.sql.ResultSet;
import tech.danfe.simplelibs.simplejdbc.core.ResultSetUtils;
import tech.danfe.simplelibs.simplejdbc.mapper.RowMapper;

/**
 *
 * @author Suraj Chhetry
 */
public class SongMapper implements RowMapper<Song> {

    @Override
    public Song mapRow(ResultSet resultSet) {
        return new Song(ResultSetUtils.getString(resultSet, "song_key", null), ResultSetUtils.getString(resultSet, "filename", null), ResultSetUtils.getDouble(resultSet, "price", 0), ResultSetUtils.getString(resultSet, "title", null));
    }

}
