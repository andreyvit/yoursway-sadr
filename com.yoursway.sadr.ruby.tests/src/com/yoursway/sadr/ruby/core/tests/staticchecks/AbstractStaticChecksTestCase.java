package com.yoursway.sadr.ruby.core.tests.staticchecks;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.dltk.core.ISourceModule;

import com.yoursway.sadr.ruby.core.runtime.WholeProjectRuntime;
import com.yoursway.sadr.ruby.core.staticchecks.IRubyProblemReporter;
import com.yoursway.sadr.ruby.core.staticchecks.IStaticCheck;
import com.yoursway.sadr.ruby.core.tests.AbstractTestCase;

public abstract class AbstractStaticChecksTestCase extends AbstractTestCase {
    
    private final List<StaticCheckAssertion> assertions = new LinkedList<StaticCheckAssertion>();
    
    protected void resetAssertions() {
        assertions.clear();
    }
    
    protected void addAssertion(IStaticCheck check, String expected) {
        assertions.add(new StaticCheckAssertion(check, expected));
    }
    
    protected void runTest() throws Exception {
        WholeProjectRuntime projectRuntime = createProjectRuntime(getClass());
        
        for (StaticCheckAssertion a : assertions)
            a.init(projectRuntime);
        
        for (ISourceModule module : projectRuntime.getSourceModules()) {
            for (StaticCheckAssertion a : assertions) {
                a.check(module);
            }
        }
    }
    
    private class AssertionRubyProblemReporter implements IRubyProblemReporter {
        
        int errors, warnings, infos;
        
        public void error(String message, int startOffset, int endOffset) {
            System.out.println("Error: " + message + " - " + startOffset + ":" + endOffset);
            errors++;
        }
        
        public void warning(String message, int startOffset, int endOffset) {
            System.out.println("Warning: " + message + " - " + startOffset + ":" + endOffset);
            warnings++;
        }
        
        public void info(String message, int startOffset, int endOffset) {
            System.out.println("Info: " + message + " - " + startOffset + ":" + endOffset);
            infos++;
        }
        
        public void reset() {
            errors = warnings = infos = 0;
        }
        
        public String actual() {
            return "" + errors + ":" + warnings + ":" + infos;
        }
        
    }
    
    private class StaticCheckAssertion {
        
        IStaticCheck check;
        String expected;
        
        public StaticCheckAssertion(IStaticCheck check, String expected) {
            this.check = check;
            this.expected = expected;
        }
        
        public void init(WholeProjectRuntime runtime) {
            check.init(runtime);
        }
        
        public void check(ISourceModule module) {
            AssertionRubyProblemReporter reporter = new AssertionRubyProblemReporter();
            reporter.reset();
            
            check.check(module, reporter);
            
            String pre = check.getClass().getSimpleName() + ":";
            Assert.assertEquals(pre + expected, pre + reporter.actual());
        }
    }
    
}
