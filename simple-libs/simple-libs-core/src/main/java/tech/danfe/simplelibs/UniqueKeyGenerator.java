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
package tech.danfe.simplelibs;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Suraj Chhetry
 */
public class UniqueKeyGenerator {

    public static synchronized String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static synchronized long generateNumeric() {
        return ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
    }

}
