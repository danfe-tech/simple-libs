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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import tech.danfe.simplelibs.simplejdbc.core.ScriptImporter;
import tech.danfe.simplelibs.simplejdbc.core.SimpleDataSource;

/**
 *
 * @author Suraj Chhetry
 */
public class ScriptImporterTest {

    static SimpleDataSource dataSource = null;

    @BeforeClass
    public static void setup() {
        dataSource = new SimpleDataSource(TestConfig.JDBC_DRIVER, TestConfig.DB_URL, TestConfig.USER, TestConfig.PASS);
    }

    @Test
    public void shouldImportSql() {
        try (Connection connection = dataSource.getConnection()) {
            ScriptImporter importer = new ScriptImporter(connection);
            importer.executeRunner();
        } catch (SQLException ex) {
            Logger.getLogger(ScriptImporterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
