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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Suraj Chhetry
 */
public class QueryParameterCollection {

    private final Map<Integer, QueryParameter> parameter;
    private int currentIndex = 0;

    private QueryParameterCollection() {
        this.parameter = new HashMap<>();
    }

    public static QueryParameterCollection newInstance() {
        return new QueryParameterCollection();
    }

    public void addParameter(QueryParameter queryParameter) {
        this.parameter.put(++currentIndex, queryParameter);
    }

    public Map<Integer, QueryParameter> getParameter() {
        return Collections.unmodifiableMap(parameter);
    }

}
