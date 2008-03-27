import os

FILE_NAME = "foo.py"

JAVA_TEST_HEAD = """package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class %s extends AbstractTypeInferencingTestCase {
"""

JAVA_TEST_TAIL = "}"

JAVA_TEST_BODY = """    @Test
    public void %s() throws Exception {
        runTest();
    }
""" 

def mkdir(dir):
    if not os.path.exists(dir):
        os.mkdir(dir)

class TestBuilder(object):
    def __init__(self, path, suite_name):
        self.java_test_file = open(path + suite_name + ".java", "w")
        self.suite_name = suite_name
        mkdir(path)
        print >>self.java_test_file, JAVA_TEST_HEAD % suite_name
        
    def __make_python_test(self, suite_name, test_name, content):
        mkdir(suite_name + "/" + test_name)
        file = open(suite_name + "/" + test_name + "/" + FILE_NAME, "w")
        print >> file, content
    
    def addTest(self, test_name, script_content):
        print >>self.java_test_file, JAVA_TEST_BODY % test_name
        self.__make_python_test(self.suite_name, test_name, script_content)

    def __del__(self):
        print >> self.java_test_file, JAVA_TEST_TAIL
