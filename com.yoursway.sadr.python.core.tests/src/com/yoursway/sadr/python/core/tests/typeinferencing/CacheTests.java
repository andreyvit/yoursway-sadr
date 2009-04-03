package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class CacheTests extends AbstractTypeInferencingTestCase {

    @Test
    public void functionReturnValuesMultipleCalls() throws Exception {
        runTest();
    }
    @Test
    public void functionReturnValuesMultipleCallsCombining() throws Exception {
        runTest();
    }
    @Test
    public void localVarCached() throws Exception {
        runTest();
    }
}