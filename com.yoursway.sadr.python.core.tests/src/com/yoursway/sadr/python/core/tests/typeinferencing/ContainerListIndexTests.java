package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class ContainerListIndexTests extends AbstractTypeInferencingTestCase {

    @Test
    public void immutableListReadByLiteralIndex() throws Exception {
        runTest();
    }
    @Test
    public void immutableListReadByUnknownIndex() throws Exception {
        runTest();
    }
    @Test
    public void immutableListReadByVariableIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByLiteralIndexReadByLiteralIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByLiteralIndexReadByLiteralIndexIneq() throws Exception {
        runTest();
    }
    @Test
    public void writeByLiteralIndexReadByUnkownIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByLiteralIndexReadByVariableIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByLiteralIndexReadByVariableIndexIneq() throws Exception {
        runTest();
    }
    @Test
    public void writeByLiteralIndexReadList() throws Exception {
        runTest();
    }
    @Test
    public void writeByUnkownIndexReadByLiteralIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByUnkownIndexReadByUnkownIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByUnkownIndexReadByVariableIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByUnkownIndexReadList() throws Exception {
        runTest();
    }
    @Test
    public void writeByVariableIndexReadByLiteralIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByVariableIndexReadByLiteralIndexIneq() throws Exception {
        runTest();
    }
    @Test
    public void writeByVariableIndexReadByUnkownIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByVariableIndexReadByVariableIndex() throws Exception {
        runTest();
    }
    @Test
    public void writeByVariableIndexReadByVariableIndexIneq() throws Exception {
        runTest();
    }
}