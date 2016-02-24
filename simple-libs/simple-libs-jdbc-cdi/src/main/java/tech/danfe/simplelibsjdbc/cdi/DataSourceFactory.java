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
package tech.danfe.simplelibsjdbc.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import tech.danfe.simplelibs.simplejdbc.core.JdbcTemplate;

/**
 *
 * @author Suraj Chhetry
 */
public class DataSourceFactory {

    @Inject
    private DataSourceConfig dataSource;

    /**
     *
     * @return
     */
    @Produces
    @SimpleJdbcTemplate
    @RequestScoped
    public JdbcTemplateWrapper produceDataSource() {
        JdbcTemplateWrapper jdbcTemplate = new JdbcTemplateWrapper(new JdbcTemplate(dataSource.getDataSource()));
        return jdbcTemplate;
    }

}
