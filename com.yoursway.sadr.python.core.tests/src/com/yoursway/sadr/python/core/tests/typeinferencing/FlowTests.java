package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class FlowTests extends AbstractTypeInferencingTestCase {
    @Test
    public void dumbFlowTest() throws Exception {
        runTest();
    }
    
    @Test
    public void functionChangesValueTest() throws Exception {
        runTest();
    }
    
    @Test
    public void ValDependsOnCondition() throws Exception {
        runTest();
    }
}
