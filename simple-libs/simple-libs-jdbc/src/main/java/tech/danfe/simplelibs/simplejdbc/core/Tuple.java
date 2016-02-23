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

import java.util.Iterator;

/**
 *
 * @author Suraj Chhetry
 * @param <X>
 * @param <Y>
 */
public class Tuple<X, Y> implements Iterable {

    final X x;
    final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getFirst() {
        return x;
    }

    public Y getSecond() {
        return y;
    }

    public Object get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            default:
                throw new IndexOutOfBoundsException("Undefined index " + index + " for a Tuple");
        }
    }

    public int size() {
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tuple tuple = (Tuple) o;

        return !(x != null ? !x.equals(tuple.x) : tuple.x != null)
                && !(y != null ? !y.equals(tuple.y) : tuple.y != null);
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }

    class TupleIterator implements Iterator {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index != size();
        }

        @Override
        public Object next() {
            return get(index++);
        }

        @Override
        public void remove() {
        }
    }

    public Iterator iterator() {
        return new TupleIterator();
    }
}
