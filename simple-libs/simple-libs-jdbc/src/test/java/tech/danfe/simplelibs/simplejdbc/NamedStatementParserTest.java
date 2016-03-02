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

import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import tech.danfe.simplelibs.simplejdbc.core.NamedSqlParseResult;
import tech.danfe.simplelibs.simplejdbc.core.NamedStatementParserUtils;

/**
 *
 * @author Suraj Chhetry
 */
public class NamedStatementParserTest {

    private static final Logger LOG = Logger.getLogger(NamedStatementParserTest.class.getName());

    @Test
    public void shouldParseSql() {
        String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:songKey12,:title,:price,:created,:note)";
        NamedSqlParseResult pre = NamedStatementParserUtils.parseNamedSql(sql);
        assertFalse(pre.getParsedSql().contains(":songKey"));
        assertEquals(6, pre.getParaMap().size());
    }
}
