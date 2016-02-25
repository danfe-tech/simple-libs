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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Suraj Chhetry
 */
public class BatchParameter {

    private final List<QueryParameter> parameters;

    public BatchParameter() {
        this.parameters = new ArrayList<>();
    }

    public BatchParameter(List<QueryParameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(QueryParameter parameter) {
        this.parameters.add(parameter);
    }

    public List<QueryParameter> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    public int size() {
        return this.parameters.size();
    }

    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }

    public QueryParameter get(int index) {
        if (!isEmpty()) {
            return this.parameters.get(index);
        }
        return null;
    }

}
