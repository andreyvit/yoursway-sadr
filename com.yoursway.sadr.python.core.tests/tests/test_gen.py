import os

JUNIT_TESTS_PATH = "../src/com/yoursway/sadr/python/core/tests/typeinferencing/"
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
    if len(dir.split('\\'))>len(dir.split('/')): splitter = '\\'
    else: splitter='/'
    current_path = ''
    for part in dir.split(splitter):
        current_path += part + '/'
        if not os.path.exists(current_path):
            os.mkdir(current_path)

class TestPackage(object):
    def __init__(self, name, init_content=""):
        self.name = name
        self.init_content =init_content
    def create(self, script_path):
        current_pack = '/'
        for part in self.name.split('.'):
            current_pack += part + '/' 
            path = script_path + current_pack
            mkdir(path)
            if not os.path.exists(path + "__init__.py"):
                open(path + "__init__.py","w")
        if len(self.init_content) > 0:
            last = open(path + "__init__.py","w")
            print >> last, self.init_content
        
class TestModule(object):
    def __init__(self, full_module_name, content):
        self.full_module_name = full_module_name
        self.content = content
    @staticmethod
    def extract_package_name(full_module_name):
        return full_module_name.rpartition('.')[0]
    @staticmethod
    def extract_module_name(full_module_name):    
        return full_module_name.rpartition('.')[2]    
    def create(self, script_path):
        package_name = self.extract_package_name(self.full_module_name)
        TestPackage(package_name).create(script_path)
        module_name = self.extract_module_name(self.full_module_name)
        path = script_path + '/' + package_name.replace('.','/') + '/'
        module = open(path + module_name + '.py', 'w')
        print >> module, self.content

class TestBuilder(object):
    def __init__(self, suite_name, path = JUNIT_TESTS_PATH):
        self.java_test_file = open(path + suite_name + ".java", "w")
        self.suite_name = suite_name
        mkdir(path)
        mkdir(suite_name)
        print >>self.java_test_file, JAVA_TEST_HEAD % suite_name
        
    def __del__(self):
        print >> self.java_test_file, JAVA_TEST_TAIL

    def __make_python_test(self, test_name, content):
        path = self.suite_name + "/" + test_name + "/"
        mkdir(path)
        file = open(path + FILE_NAME, "w")
        print >> file, content
    
    def addTest(self, test_name, script_content):
        print >>self.java_test_file, JAVA_TEST_BODY % test_name
        self.__make_python_test(test_name, script_content)

    def addModularTest(self, test_name, script_content, modules):
        script_path = self.suite_name + "/" + test_name + "/"
        self.addTest(test_name, script_content)
        for module in modules:
            module.create(script_path)
