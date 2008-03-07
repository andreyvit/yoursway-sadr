package com.yoursway.sadr.ruby.core.tests.typeinferencing;

import org.junit.Test;

public class MiscTypeInferencingTests extends AbstractTypeInferencingTestCase {
    
    @Test
    public void functionReturningInt() throws Exception {
        runTest();
    }
    
    @Test
    public void acrossSeveralFiles() throws Exception {
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
