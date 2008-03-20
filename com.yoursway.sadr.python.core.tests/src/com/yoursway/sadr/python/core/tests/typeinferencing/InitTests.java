package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

/**
 * Tests on __init__ method processing.
 */
public class InitTests extends AbstractTypeInferencingTestCase {
    @Test
    public void __init__ExecutionCheck() throws Exception {
        runTest();
    }
    
    @Test
    public void __new__ExecutionCheck() throws Exception {
        runTest();
    }
}
