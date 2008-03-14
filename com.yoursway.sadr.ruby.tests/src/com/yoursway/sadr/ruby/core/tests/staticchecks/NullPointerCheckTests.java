package com.yoursway.sadr.ruby.core.tests.staticchecks;

import org.junit.Test;

import com.yoursway.sadr.ruby.core.staticchecks.NullPointerCheck;

public class NullPointerCheckTests extends AbstractStaticChecksTestCase {
    
    @Test
    public void nilLocalVariable() throws Exception {
        resetAssertions();
        //> addAssertion(new MethodCallCheck(), "0:0:0");
        addAssertion(new NullPointerCheck(), "0:1:0");
        runTest();
    }
    
    @Test
    public void nilArgument() throws Exception {
        resetAssertions();
        //> addAssertion(new MethodCallCheck(), "0:0:0");
        addAssertion(new NullPointerCheck(), "0:1:0");
        runTest();
    }
    
    @Test
    public void condition() throws Exception {
        resetAssertions();
        //> addAssertion(new MethodCallCheck(), "0:0:0");
        addAssertion(new NullPointerCheck(), "0:0:0");
        runTest(); //! exception
    }
    
}
