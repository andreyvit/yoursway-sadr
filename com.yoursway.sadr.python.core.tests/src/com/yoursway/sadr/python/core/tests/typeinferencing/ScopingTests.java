package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class ScopingTests extends AbstractTypeInferencingTestCase {

    @Test
    public void fileLevelVariableRead() throws Exception {
        runTest();
    }
    @Test
    public void fileLocalVariable() throws Exception {
        runTest();
    }
}