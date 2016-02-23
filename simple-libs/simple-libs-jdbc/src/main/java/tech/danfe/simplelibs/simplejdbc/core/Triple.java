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
 * @param <X>
 * @param <Y>
 * @param <Z>
 */
public class Triple<X, Y, Z> extends Tuple<X, Y> {

    final Z z;

    public Triple(X x, Y y, Z z) {
        super(x, y);
        this.z = z;
    }

    public Z getThird() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Triple triple = (Triple) o;

        return !(z != null ? !z.equals(triple.z) : triple.z != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (z != null ? z.hashCode() : 0);
        return result;
    }

    @Override
    public Object get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            default:
                throw new IndexOutOfBoundsException("Undefined index " + index + " for a Triple");
        }
    }

    @Override
    public int size() {
        return 3;
    }
}
