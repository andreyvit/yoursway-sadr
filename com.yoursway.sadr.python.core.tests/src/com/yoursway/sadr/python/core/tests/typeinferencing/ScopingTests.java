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
    
    @Test
    public void closureVarRead() throws Exception {
        runTest();
    }
    
    @Test
    public void classScopeDeceit() throws Exception {
        runTest();
    }
    
    @Test
    public void inductionVarDef() throws Exception {
        runTest();
    }
    
    @Test
    public void conditionallyDefinedVar() throws Exception {
        runTest();
    }
    
    @Test
    public void builtinScope() throws Exception {
        runTest();
    }
    
    @Test
    public void flowSensitiveClosureVarRead() throws Exception {
        runTest();
    }
    
}