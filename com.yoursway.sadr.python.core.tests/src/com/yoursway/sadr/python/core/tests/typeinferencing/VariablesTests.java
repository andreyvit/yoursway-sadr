package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public class VariablesTests extends AbstractTypeInferencingTestCase {
    
    @Test
    public void localVariableType() throws Exception {
        runTest();
    }
    
    @Test
    public void localVariableAssignments() throws Exception {
        runTest();
    }
    
    @Test
    public void fieldType() throws Exception {
        runTest();
    }
    
    @Test
    public void globalVariableType() throws Exception {
        runTest();
    }
    
    @Test
    public void funcAssignedToGlobal() throws Exception {
        runTest();
    }
 
}
