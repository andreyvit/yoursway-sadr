package com.yoursway.sadr.ruby.core.tests.staticchecks;

import org.junit.Test;

import com.yoursway.sadr.ruby.core.staticchecks.MethodCallCheck;

public class MethodCallCheckTests extends AbstractStaticChecksTestCase {
    
    @Test
    public void callMethod() throws Exception {
        resetAssertions();
        addAssertion(new MethodCallCheck(), "0:4:1");
        runTest();
    }
    
    @Test
    public void callMethodWithArguments() throws Exception {
        resetAssertions();
        addAssertion(new MethodCallCheck(), "0:2:0");
        runTest();
    }
    
    @Test
    public void callProcedure() throws Exception {
        resetAssertions();
        addAssertion(new MethodCallCheck(), "0:2:0");
        runTest();
    }
    
    @Test
    public void callProcedureFromMethod() throws Exception {
        resetAssertions();
        addAssertion(new MethodCallCheck(), "0:1:0");
        runTest();
    }
    
    @Test
    public void callProcedureWithArguments() throws Exception {
        resetAssertions();
        addAssertion(new MethodCallCheck(), "0:2:0");
        runTest();
    }
    
    @Test
    public void callUndefinedClassMethod() throws Exception {
        resetAssertions();
        addAssertion(new MethodCallCheck(), "0:1:2");
        runTest();
    }
    
}
