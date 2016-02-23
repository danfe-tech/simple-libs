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

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Suraj Chhetry
 */
public class MappedParameterUtils {

    public static void fillNamedParameter(NamedParameterStatement statement, Map<String, Object> parameters) {
        Iterator<Map.Entry<String, Object>> iterator = parameters.entrySet().iterator();
        while (iterator.hasNext()) {
            try {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                statement.setObject(entry.getKey(), entry.getValue());
            } catch (SQLException ex) {
                Logger.getLogger(MappedParameterUtils.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
