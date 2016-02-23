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

import java.util.List;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import tech.danfe.simplelibs.ObjectUtils;
import tech.danfe.simplelibs.simplejdbc.core.JdbcTemplate;
import tech.danfe.simplelibs.simplejdbc.core.SimpleDataSource;

/**
 *
 * @author Suraj Chhetry
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcTemplateTest {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    static final String DB_URL = "jdbc:hsqldb:mem:testdb";
    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";
    private static final Logger LOG = Logger.getLogger(JdbcTemplateTest.class.getName());
    private JdbcTemplate jdbcHelper = null;

    @BeforeClass
    public static void setup() {
        SimpleDataSource dataSource = new SimpleDataSource(JDBC_DRIVER, DB_URL, USER, PASS);
        JdbcTemplate jdbcHelper = new JdbcTemplate(dataSource);
        //create table
        String SONG_TABLE = "CREATE TABLE songs\n"
                + "(\n"
                + "  song_key character varying(255) NOT NULL,\n"
                + "  filename character varying(255),\n"
                + "  price double precision NOT NULL,\n"
                + "  title character varying(255),\n"
                + "  CONSTRAINT songs_pkey PRIMARY KEY (song_key)\n"
                + ")";
        jdbcHelper.execute(SONG_TABLE);
    }

    @Before
    public void initSetup() {
        SimpleDataSource dataSource = new SimpleDataSource(JDBC_DRIVER, DB_URL, USER, PASS);
        this.jdbcHelper = new JdbcTemplate(dataSource);
    }

    @Test
    public void test_insert() {
        Song song = new Song("12478", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price) values (:songKey,:fileName,:title,:price)";
        this.jdbcHelper.execute(sql, ObjectUtils.toMap(song));
        List<Song> songs = jdbcHelper.queryForList("select song_key,filename from songs", new SongMapper());
        assertEquals(1, songs.size());
    }

    @Test
    public void test_transaction_one() {
        this.jdbcHelper.beginTransaction();
        String key = String.valueOf(System.nanoTime());
        Song song = new Song(key, "test Name", 10, "Named param from transaction # " + key);
        String sql = "Insert into songs (song_key,filename,title,price) values (:songKey,:fileName,:title,:price)";
        this.jdbcHelper.execute(sql, ObjectUtils.toMap(song));
        List<Song> songs = jdbcHelper.queryForList("select * from songs", new SongMapper());
        assertEquals(1, songs.size());
        this.jdbcHelper.commitTransaction();
    }

    @Test
    public void test_transaction_nested() {
        String key = String.valueOf(System.nanoTime());
        Song song = new Song(key, "test Name", 10, "Named param from transaction # " + key);
        this.jdbcHelper.beginTransaction();
        this.nextInsert();
        String sql = "Insert into songs (song_key,filename,title,price) values (:songKey,:fileName,:title,:price)";
        this.jdbcHelper.execute(sql, ObjectUtils.toMap(song));
        List<Song> songs = jdbcHelper.queryForList("select * from songs", new SongMapper());
        assertEquals(2, songs.size());
        //this.jdbcHelper.getConnection().rollback(savepoint);
        this.jdbcHelper.rollbackTransaction();
        List<Song> songsTemp = jdbcHelper.queryForList("select * from songs", new SongMapper());
        assertEquals(0, songsTemp.size());

    }

    @Test
    public void test_transaction_rollback() {
        this.jdbcHelper.beginTransaction();
        String key = String.valueOf(System.nanoTime());
        Song song = new Song(key, "test Name", 10, "Named param from transaction # " + key);
        String sql = "Insert into songs (song_key,filename,title,price) values (:songKey,:fileName,:title,:price)";
        this.jdbcHelper.execute(sql, ObjectUtils.toMap(song));
        List<Song> songs = jdbcHelper.queryForList("select * from songs", new SongMapper());
        assertEquals(1, songs.size());
        this.jdbcHelper.rollbackTransaction();
        List<Song> songsTemp = jdbcHelper.queryForList("select * from songs", new SongMapper());
        assertEquals(0, songsTemp.size());
    }

    private void nextInsert() {
        String sql = "Insert into songs (song_key,filename,title,price) values (:songKey,:fileName,:title,:price)";
        String key = String.valueOf(System.nanoTime());
        Song song = new Song(key, "test Name", 10, "Named param from transaction # " + key);
        this.jdbcHelper.beginTransaction();
        this.jdbcHelper.execute(sql, ObjectUtils.toMap(song));
        // this.jdbcHelper.commitTransaction();
    }

    @After
    public void clean() {
        this.jdbcHelper.execute("delete from songs");
    }
}
