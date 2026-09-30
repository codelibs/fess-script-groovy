/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.fess.script.groovy;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.runtime.GStringImpl;
import org.codelibs.fess.crawler.serializer.DataSerializer;
import org.junit.jupiter.api.Test;

public class GStringSerializerTest extends UnitScriptTestCase {

    private DataSerializer newDataSerializer() {
        return new DataSerializer() {
            @Override
            protected String getSerializerType() {
                return KRYO;
            }
        };
    }

    /**
     * Test that a GString is rejected until the serializer registers it
     */
    @Test
    public void test_gstringIsRejectedWithoutTheSerializer() {
        final DataSerializer dataSerializer = newDataSerializer();
        try {
            dataSerializer.fromObjectToBinary(new GStringImpl(new Object[] { "b" }, new String[] { "a", "c" }));
            fail("A GString must not be serializable before the serializer is registered");
        } catch (final RuntimeException e) {
            // expected
        }
    }

    /**
     * Test that a registered GString is written as its text and read back as a String
     */
    @Test
    public void test_gstringRoundTrip() {
        final DataSerializer dataSerializer = newDataSerializer();
        dataSerializer.register(GStringImpl.class, new GStringSerializer());

        final GStringImpl original = new GStringImpl(new Object[] { "world", 3 }, new String[] { "hello ", " x", "" });
        final Map<String, Object> map = new HashMap<>();
        map.put("field", original);

        @SuppressWarnings("unchecked")
        final Map<String, Object> restored =
                (Map<String, Object>) dataSerializer.fromBinaryToObject(dataSerializer.fromObjectToBinary(map));
        assertEquals(String.class, restored.get("field").getClass());
        assertEquals("hello world x3", restored.get("field"));
    }

    /**
     * Test that the values a GString holds are not written
     */
    @Test
    public void test_gstringValuesAreNotStored() {
        final DataSerializer dataSerializer = newDataSerializer();
        dataSerializer.register(GStringImpl.class, new GStringSerializer());

        final Object[] values = { new Object() {
            @Override
            public String toString() {
                return "text";
            }
        } };
        assertEquals("text",
                dataSerializer.fromBinaryToObject(dataSerializer.fromObjectToBinary(new GStringImpl(values, new String[] { "", "" }))));
    }
}
