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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Suraj Chhetry
 */
public class ScriptImporter {

    private final Connection connection;

    public ScriptImporter(Connection connection) {
        this.connection = connection;
    }

    public void executeRunner() {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream("META-INF/import.sql")) {
            Scanner scanner = new Scanner(input);
            String pattern = "(;(\\r)?\\n)|(--\\n)";
            scanner.useDelimiter(pattern);
            try (Statement statement = connection.createStatement()) {
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    if (line.startsWith("/*!") && line.endsWith("*/")) {
                        int index = line.indexOf(" ");
                        line = line.substring(index + 1, line.length() - " */".length());
                    }
                    if (line.trim().length() > 0) {
                        statement.execute(line);
                    }
                }

            } catch (SQLException exception) {
                throw new DataAccessException(exception);
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

}
