package com.esko.dtl.core.tests.typeinferencing;

import org.junit.Test;

public class EvalTests extends AbstractTypeInferencingTestCase {
    
    @Test
    public void methodDeclaration() throws Exception {
        runTest();
    }
    
    @Test
    public void fieldAssignment() throws Exception {
        runTest();
    }
    
    @Test
    public void fieldAssignmentInMethodScope() throws Exception {
        runTest();
    }
    
    @Test
    public void methodsInCycle() throws Exception {
        runTest();
    }
    
}
