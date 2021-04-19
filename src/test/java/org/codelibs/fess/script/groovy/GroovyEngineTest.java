package org.codelibs.fess.script.groovy;

import java.util.HashMap;
import java.util.Map;

import org.codelibs.fess.util.ComponentUtil;
import org.dbflute.utflute.lastaflute.LastaFluteTestCase;

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
    public void setUp() throws Exception {
        super.setUp();
        groovyEngine = new GroovyEngine();
    }

    @Override
    public void tearDown() throws Exception {
        ComponentUtil.setFessConfig(null);
        super.tearDown();
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
