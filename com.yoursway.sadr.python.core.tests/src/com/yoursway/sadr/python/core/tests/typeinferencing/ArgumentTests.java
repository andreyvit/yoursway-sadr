package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class ArgumentTests extends AbstractTypeInferencingTestCase {

    @Test
    public void argumentTypeByCalledMethods() throws Exception {
        runTest();
    }
    @Test
    public void methodArgumentTypeByCallers() throws Exception {
        runTest();
    }
    @Test
    public void procedureArgumentTypeByCallers() throws Exception {
        runTest();
    }
}