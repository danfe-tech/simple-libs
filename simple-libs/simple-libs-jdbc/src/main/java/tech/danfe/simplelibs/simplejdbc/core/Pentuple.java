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
 * @param <W>
 * @param <Q> 
 */
public class Pentuple<X, Y, Z, W, Q> extends Quadruple<X, Y, Z, W> {

    final Q q;

    public Pentuple(X x, Y y, Z z, W w, Q q) {
        super(x, y, z, w);
        this.q = q;
    }

    public Q getFift() {
        return q;
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

        Pentuple pentuple = (Pentuple) o;

        return !(q != null ? !q.equals(pentuple.q) : pentuple.q != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (q != null ? q.hashCode() : 0);
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
            case 3:
                return w;
            case 4:
                return q;
            default:
                throw new IndexOutOfBoundsException("Undefined index " + index + " for a Pentuple");
        }
    }

    @Override
    public int size() {
        return 5;
    }
}
