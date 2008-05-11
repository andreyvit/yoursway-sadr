package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class EvalTests extends AbstractTypeInferencingTestCase {

    @Test
    public void fieldAssignment() throws Exception {
        runTest();
    }

    @Test
    public void fieldAssignmentInMethodScope() throws Exception {
        runTest();
    }

    @Test
    public void methodDeclaration() throws Exception {
        runTest();
    }

    @Test
    public void methodsInCycle() throws Exception {
        runTest();
    }

}
