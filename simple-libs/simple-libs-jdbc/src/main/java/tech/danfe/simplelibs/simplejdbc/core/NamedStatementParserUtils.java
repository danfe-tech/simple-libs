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
 * @author Suraj Chhetry
 */
public class NamedStatementParserUtils {

    private NamedStatementParserUtils() {
    }

    public static String parseNamedSql(String query) {
        int length = query.length();
        StringBuilder parsedQuery = new StringBuilder(length);
        boolean hasSingleQuote = false;
        boolean hasDoubleQuote = false;
        for (int i = 0; i < length; i++) {
            char c = query.charAt(i);
            if (hasSingleQuote) {
                if (c == '\'') {
                    hasSingleQuote = false;
                }
            } else if (hasDoubleQuote) {
                if (c == '"') {
                    hasDoubleQuote = false;
                }
            } else if (c == '\'') {
                hasSingleQuote = true;
            } else if (c == '"') {
                hasDoubleQuote = true;
            } else if (c == ':' && i + 1 < length
                    && Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                int j = i + 2;
                while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
                    j++;
                }
                String name = query.substring(i + 1, j);
                c = '?'; 
                i += name.length();
            }
            parsedQuery.append(c);
        }
        return parsedQuery.toString();

    }
}
