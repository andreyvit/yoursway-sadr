package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class MiscTypeInferencingTests extends AbstractTypeInferencingTestCase {
    
    @Test
    public void acrossSeveralFiles() throws Exception {
        runTest();
    }
    
    @Test
    public void methodReturningInt() throws Exception {
        runTest();
    }
    
    @Test
    public void functionReturningInt() throws Exception {
        runTest();
    }
    
    @Test
    public void inheritedMethodHiding() throws Exception {
        runTest();
    }
    
    @Test
    public void undefinedVariable() throws Exception {
        runTest();
    }
}