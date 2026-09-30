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

import org.codelibs.fess.crawler.serializer.DataSerializer;
import org.codelibs.fess.util.ComponentUtil;
import org.codehaus.groovy.runtime.GStringImpl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Serializes a GString as its text so that a crawl-time field script returning
 * <code>"...${x}..."</code> can be stored in the crawl data.
 *
 * <p>Only the text is written, and it is read back as a String. The values a GString holds, which
 * can include closures, are not stored. A String is what the crawl data needs to hold: the
 * document is sent to the search engine after it is read back, and the search engine cannot write
 * a GString.</p>
 */
public class GStringSerializer extends Serializer<Object> {

    /**
     * Default constructor for GStringSerializer.
     */
    public GStringSerializer() {
        // nothing
    }

    /**
     * Registers this serializer for GStringImpl with the crawler data serializer.
     * Called by the DI container after property injection.
     */
    public void register() {
        final DataSerializer dataSerializer = ComponentUtil.getComponent("dataSerializer");
        dataSerializer.register(GStringImpl.class, this);
    }

    @Override
    public void write(final Kryo kryo, final Output output, final Object object) {
        output.writeString(object.toString());
    }

    @Override
    public Object read(final Kryo kryo, final Input input, final Class<? extends Object> type) {
        return input.readString();
    }
}
