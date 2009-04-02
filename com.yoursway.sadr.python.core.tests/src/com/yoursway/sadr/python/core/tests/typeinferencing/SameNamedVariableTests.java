package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class SameNamedVariableTests extends AbstractTypeInferencingTestCase {

    @Test
    public void classAndIf() throws Exception {
        runTest();
    }
    @Test
    public void functionArgumentAmbiguity() throws Exception {
        runTest();
    }
    @Test
    public void globalAndArgument() throws Exception {
        runTest();
    }
    @Test
    public void globalAndClassStatic() throws Exception {
        runTest();
    }
    @Test
    public void globalAndClassStatic2() throws Exception {
        runTest();
    }
    @Test
    public void globalAndGlobal() throws Exception {
        runTest();
    }
    @Test
    public void globalAndInnerLocal() throws Exception {
        runTest();
    }
    @Test
    public void globalAndInnerLocal2() throws Exception {
        runTest();
    }
    @Test
    public void globalAndInnerLocal3() throws Exception {
        runTest();
    }
    @Test
    public void globalAndLocal() throws Exception {
        runTest();
    }
    @Test
    public void localAndIf() throws Exception {
        runTest();
    }
    @Test
    public void localAndInner() throws Exception {
        runTest();
    }
    @Test
    public void localAndInnerGlobal() throws Exception {
        runTest();
    }
    @Test
    public void localAndInnerGlobal2() throws Exception {
        runTest();
    }
    @Test
    public void localAndInnerGlobal3() throws Exception {
        runTest();
    }
    @Test
    public void localAndYield() throws Exception {
        runTest();
    }
    @Test
    public void localAndYield2() throws Exception {
        runTest();
    }
    @Test
    public void localInnerAccess() throws Exception {
        runTest();
    }
}