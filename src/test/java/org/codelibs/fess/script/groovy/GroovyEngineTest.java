/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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

import org.codelibs.fess.util.ComponentUtil;
import org.dbflute.utflute.lastaflute.LastaFluteTestCase;
import org.junit.jupiter.api.TestInfo;

public class GroovyEngineTest extends LastaFluteTestCase {
    public GroovyEngine groovyEngine;

    @Override
    protected String prepareConfigFile() {
        return "test_app.xml";
    }

    @Override
    protected boolean isSuppressTestCaseTransaction() {
        return true;
    }

    @Override
    protected void setUp(final TestInfo testInfo) throws Exception {
        super.setUp(testInfo);
        groovyEngine = new GroovyEngine();
    }

    @Override
    protected void tearDown(final TestInfo testInfo) throws Exception {
        ComponentUtil.setFessConfig(null);
        super.tearDown(testInfo);
    }

    public void test_evaluate() {
        final Map<String, Object> params = new HashMap<>();
        assertNull(groovyEngine.evaluate("", params));
        assertEquals("", groovyEngine.evaluate("return ''", params));
        assertEquals(1, groovyEngine.evaluate("return 1", params));

        params.put("test", "123");
        assertEquals("123", groovyEngine.evaluate("return test", params));
    }

    public void test_getName() {
        assertEquals("groovy", groovyEngine.getName());
    }
}
