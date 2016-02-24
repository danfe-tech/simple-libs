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
public class QueryParameter {

    private final String name;
    private final Object value;
    private final ParameterType type;

    public enum ParameterType {
        Object,
        String,
        Date,
        Integer,
        Double
    }

    public QueryParameter(String name, Object value) {
        this.name = name;
        this.value = value;
        this.type = ParameterType.Object;
    }

    public QueryParameter(String name, Object value, ParameterType type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public ParameterType getType() {
        return type;
    }

}
