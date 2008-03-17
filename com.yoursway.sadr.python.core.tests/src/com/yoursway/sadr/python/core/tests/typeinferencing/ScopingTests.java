package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public class ScopingTests extends AbstractTypeInferencingTestCase {
    
    @Test
    public void fileLocalVariable() throws Exception {
        runTest();
    }
    
    @Test
    public void fileLevelVariableRead() throws Exception {
        runTest();
    }
    
}
