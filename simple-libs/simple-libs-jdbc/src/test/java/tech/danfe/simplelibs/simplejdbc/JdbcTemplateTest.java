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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tech.danfe.simplelibs.simplejdbc.core.DBUtils;
import tech.danfe.simplelibs.simplejdbc.core.JdbcTemplate;
import tech.danfe.simplelibs.simplejdbc.core.QueryParameter;
import tech.danfe.simplelibs.simplejdbc.core.SimpleDataSource;
import static org.junit.Assert.*;
import tech.danfe.simplelibs.simplejdbc.core.BatchParameter;

/**
 *
 * @author Suraj Chhetry
 */
public class JdbcTemplateTest {

    private static final Logger LOG = Logger.getLogger(JdbcTemplateTest.class.getName());
    private JdbcTemplate jdbcTemplate = null;
    static SimpleDataSource dataSource = null;

    @BeforeClass
    public static void setup() {
        dataSource = new SimpleDataSource(TestConfig.JDBC_DRIVER, TestConfig.DB_URL, TestConfig.USER, TestConfig.PASS);
        JdbcTemplate jdbcHelper = new JdbcTemplate(dataSource);
        DBUtils dBUtils = new DBUtils(dataSource);
        if (!dBUtils.doesTableExists(TestConfig.SONG_TABLE_NAME)) {
            jdbcHelper.executeUpdate(TestConfig.tableCreateScript());
        }

    }

    @After
    public void clean() {
        this.jdbcTemplate.executeUpdate("delete from songs");
    }

    @Before
    public void initSetup() {
        // SimpleDataSource simpleDataSource = new SimpleDataSource(TestConfig.JDBC_DRIVER, TestConfig.DB_URL, TestConfig.USER, TestConfig.PASS);
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Test
    public void test_insert() {
        Song song = new Song("12478", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
        List<QueryParameter> parameters = new ArrayList<>();
        parameters.add(new QueryParameter("songKey", song.getSongKey()));
        parameters.add(new QueryParameter("fileName", song.getFileName()));
        parameters.add(new QueryParameter("title", song.getTitle()));
        parameters.add(new QueryParameter("price", song.getPrice()));
        parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameters.add(new QueryParameter("note", "test"));
        this.jdbcTemplate.executeUpdate(sql, parameters);
        List<Song> songs = jdbcTemplate.queryForList("select song_key,filename from songs", new SongMapper());
        assertEquals(1, songs.size());

        List<QueryParameter> queryParameters = new ArrayList<>();
        queryParameters.add(new QueryParameter("title", song.getTitle()));
        Song song1 = jdbcTemplate.queryForObject("Select *  from songs where title=:title", new SongMapper(), queryParameters);
        assertNotNull(song1);
    }

    @Test
    public void test_transaction_with_rollback() {
        Song song = new Song("12478", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
        List<QueryParameter> parameters = new ArrayList<>();
        parameters.add(new QueryParameter("songKey", song.getSongKey()));
        parameters.add(new QueryParameter("fileName", song.getFileName()));
        parameters.add(new QueryParameter("title", song.getTitle()));
        parameters.add(new QueryParameter("price", song.getPrice()));
        parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameters.add(new QueryParameter("note", "test"));
        this.jdbcTemplate.beginTransaction();
        this.jdbcTemplate.executeUpdate(sql, parameters);
        this.jdbcTemplate.rollbackTransaction();
        List<Song> songs = jdbcTemplate.queryForList("select song_key,filename from songs", new SongMapper());
        assertEquals(0, songs.size());
    }

    @Test
    public void test_transaction_with_commit() {
        Song song = new Song("12478", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
        List<QueryParameter> parameters = new ArrayList<>();
        parameters.add(new QueryParameter("songKey", song.getSongKey()));
        parameters.add(new QueryParameter("fileName", song.getFileName()));
        parameters.add(new QueryParameter("title", song.getTitle()));
        parameters.add(new QueryParameter("price", song.getPrice()));
        parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameters.add(new QueryParameter("note", "test"));
        this.jdbcTemplate.beginTransaction();
        this.jdbcTemplate.executeUpdate(sql, parameters);
        this.jdbcTemplate.commitTransaction();
        List<Song> songs = jdbcTemplate.queryForList("select song_key,filename from songs", new SongMapper());
        assertEquals(1, songs.size());
    }

    private void insert_only() {
        Song song = new Song("12478233", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
        List<QueryParameter> parameters = new ArrayList<>();
        parameters.add(new QueryParameter("songKey", song.getSongKey()));
        parameters.add(new QueryParameter("fileName", song.getFileName()));
        parameters.add(new QueryParameter("title", song.getTitle()));
        parameters.add(new QueryParameter("price", song.getPrice()));
        parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameters.add(new QueryParameter("note", "test"));
        this.jdbcTemplate.executeUpdate(sql, parameters);

    }

    private void insert_with_new_datasource() {
        JdbcTemplate jdbcTemplate2 = new JdbcTemplate(dataSource);
        Song song = new Song("12478233", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
        List<QueryParameter> parameters = new ArrayList<>();
        parameters.add(new QueryParameter("songKey", song.getSongKey()));
        parameters.add(new QueryParameter("fileName", song.getFileName()));
        parameters.add(new QueryParameter("title", song.getTitle()));
        parameters.add(new QueryParameter("price", song.getPrice()));
        parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameters.add(new QueryParameter("note", "test"));
        jdbcTemplate2.executeUpdate(sql, parameters);
    }

    @Test
    public void test_nested_transaction_with_commit() {
        Song song = new Song("12478", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
        List<QueryParameter> parameters = new ArrayList<>();
        parameters.add(new QueryParameter("songKey", song.getSongKey()));
        parameters.add(new QueryParameter("fileName", song.getFileName()));
        parameters.add(new QueryParameter("title", song.getTitle()));
        parameters.add(new QueryParameter("price", song.getPrice()));
        parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameters.add(new QueryParameter("note", "test"));
        this.jdbcTemplate.beginTransaction();
        this.insert_only();
        this.jdbcTemplate.executeUpdate(sql, parameters);
        this.jdbcTemplate.commitTransaction();
        List<Song> songs = jdbcTemplate.queryForList("select song_key,filename from songs", new SongMapper());
        assertEquals(2, songs.size());
    }

    @Test
    public void test_nested_transaction_with_rollback() {
        Song song = new Song("12478", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
        List<QueryParameter> parameters = new ArrayList<>();
        parameters.add(new QueryParameter("songKey", song.getSongKey()));
        parameters.add(new QueryParameter("fileName", song.getFileName()));
        parameters.add(new QueryParameter("title", song.getTitle()));
        parameters.add(new QueryParameter("price", song.getPrice()));
        parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameters.add(new QueryParameter("note", "test"));
        this.jdbcTemplate.beginTransaction();
        this.insert_with_new_datasource();
        this.jdbcTemplate.executeUpdate(sql, parameters);
        this.jdbcTemplate.rollbackTransaction();
        List<Song> songs = jdbcTemplate.queryForList("select song_key,filename from songs", new SongMapper());
        assertEquals(1, songs.size());
    }

    @Test
    public void test_batch_insert() {
        Song song = new Song("12478", "test Name", 10, "Named param");
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:fileName12,:price,:created,:note)";
        List<BatchParameter> parameters = new ArrayList<>();
        // first batch parameter
        BatchParameter parameter = new BatchParameter();
        parameter.addParameter(new QueryParameter("songKey", "4502"));
        parameter.addParameter(new QueryParameter("fileName", song.getFileName()));
        parameter.addParameter(new QueryParameter("fileName12", song.getTitle()));
        parameter.addParameter(new QueryParameter("price", song.getPrice()));
        parameter.addParameter(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameter.addParameter(new QueryParameter("note", "test"));
        parameters.add(parameter);

        // second batch parameter
        BatchParameter parameterTwo = new BatchParameter();
        parameterTwo.addParameter(new QueryParameter("songKey", song.getSongKey()));
        parameterTwo.addParameter(new QueryParameter("fileName", song.getFileName()));
        parameterTwo.addParameter(new QueryParameter("fileName12", song.getTitle()));
        parameterTwo.addParameter(new QueryParameter("price", song.getPrice()));
        parameterTwo.addParameter(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
        parameterTwo.addParameter(new QueryParameter("note", "test"));
        parameters.add(parameterTwo);

        int[] result = this.jdbcTemplate.executeBatch(sql, parameters);
        assertEquals(2, result.length);
    }

}
