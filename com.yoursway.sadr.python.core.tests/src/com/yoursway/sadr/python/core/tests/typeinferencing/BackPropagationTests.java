package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class BackPropagationTests extends AbstractTypeInferencingTestCase {
    
    @Test
    public void cachingIssue() throws Exception {
        runTest();
    }
    
    @Test
    public void cyclicCalls() throws Exception {
        //runTest();
    }
    
    @Test
    public void extraSin() throws Exception {
        runTest();
    }
    
    @Test
    public void infiniteLoop() throws Exception {
        runTest();
    }
    
    @Test
    public void multipleContexts() throws Exception {
        runTest();
    }
    
    @Test
    public void nestedCall() throws Exception {
        runTest();
    }
    
    @Test
    public void progression() throws Exception {
        runTest();
    }
    
    @Test
    public void unusedArgumentIsNotEvaluated() throws Exception {
        runTest();
    }
}