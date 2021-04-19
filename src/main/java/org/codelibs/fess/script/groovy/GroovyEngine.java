package org.codelibs.fess.script.groovy;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codelibs.fess.exception.JobProcessingException;
import org.codelibs.fess.script.AbstractScriptEngine;
import org.lastaflute.di.core.factory.SingletonLaContainerFactory;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;

public class GroovyEngine extends AbstractScriptEngine {
    private static final Logger logger = LogManager.getLogger(GroovyEngine.class);

    @Override
    public Object evaluate(String template, Map<String, Object> paramMap) {
        final Map<String, Object> bindingMap = new HashMap<>(paramMap);
        bindingMap.put("container", SingletonLaContainerFactory.getContainer());
        final GroovyShell groovyShell = new GroovyShell(new Binding(bindingMap));
        try {
            return groovyShell.evaluate(template);
        } catch (final JobProcessingException e) {
            throw e;
        } catch (final Exception e) {
            logger.warn("Failed to evalue groovy script: {} => {}", template, paramMap, e);
            return null;
        } finally {
            final GroovyClassLoader loader = groovyShell.getClassLoader();
            loader.clearCache();
        }
    }

    @Override
    protected String getName() {
        return "groovy";
    }

}
