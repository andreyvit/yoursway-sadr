package com.yoursway.sadr.ruby.core.tests.staticchecks;

import org.junit.Test;

import com.yoursway.sadr.ruby.core.staticchecks.MethodCallCheck;

public class MethodCallCheckTests extends AbstractStaticChecksTestCase {
    
    @Test
    public void first() throws Exception {
        resetAssertions();
        addAssertion(new MethodCallCheck(), "2:0:0");
        runTest();
    }
    
}
