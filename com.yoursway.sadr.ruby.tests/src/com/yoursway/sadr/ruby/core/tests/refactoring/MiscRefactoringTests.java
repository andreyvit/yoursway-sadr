package com.yoursway.sadr.ruby.core.tests.refactoring;

import org.junit.Test;

public class MiscRefactoringTests extends AbstractRefactoringTestCase {
    
    @Test
    public void simpleMethodRename() throws Exception {
        runTest("Bar", "foo", "bar");
    }
    
}
