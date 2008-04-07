package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { MiscTypeInferencingTests.class, ScopingTests.class, ArgumentTests.class,
        ArrayHandlingTests.class, MiscValueInferencingTests.class, CacheTests.class, ControlFlowTests.class,
        TruethValuesTests.class })
public class MustPassTests {
    @Test
    public void tests() throws Exception {
        //InitTests
        (new InitTests()).__new__ExecutionCheck();
        // VariablesTests
        VariablesTests vt = new VariablesTests();
        vt.localVariableAssignments();
        vt.localVariableType();
        vt.fieldType();
        vt.externalFieldAssignment();
        vt.classAsStorage();
        vt.equalNamesAssignment();
    }
}
