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

/**
 *
 * @author Suraj Chhetry
 */
public class TestConfig {

    private TestConfig() {
    }

    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    public static final String DB_URL = "jdbc:hsqldb:mem:testdb";
    //  Database credentials
    public static final String USER = "sa";
    public static final String PASS = "";
    public static final String SONG_TABLE_NAME = "songs";

    public static String tableCreateScript() {
        return "CREATE TABLE "+SONG_TABLE_NAME +"\n"
                + "(\n"
                + "  song_key character varying(255) NOT NULL,\n"
                + "  filename character varying(255),\n"
                + "  price double precision NOT NULL,\n"
                + "  title character varying(255),\n"
                + "  created date,\n "
                + "   note character varying(255), \n"
                + "  CONSTRAINT songs_pkey PRIMARY KEY (song_key)\n"
                + ")";
    }

}
