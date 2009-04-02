import os

JUNIT_TESTS_PATH = "../src/com/yoursway/sadr/python/core/tests/typeinferencing/"
OLD_FILE_NAME = "foo.py"

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

def mkdirs(newdir, mode=0777):
    if os.path.exists(newdir): return
    try: os.makedirs(newdir, mode)
    except OSError, err:
        import errno
        # Reraise the error unless it's about an already existing directory
        if err.errno != errno.EEXIST or not os.path.isdir(newdir):
            raise

class TestPackage(object):
    def __init__(self, name, init_content=""):
        self.name = name
        self.init_content = init_content

    def create(self, root_path):
        current_pack = '/'
        for part in self.name.split('.'):
            current_pack += part + '/' 
            path = root_path + current_pack
            mkdirs(path)
            if not os.path.exists(path + "__init__.py"):
                open(path + "__init__.py", "wb")
        if self.init_content:
            last = open(path + "__init__.py", "wb")
            print >> last, self.init_content
        
class TestModule(object):
    def __init__(self, full_module_name, content):
        self.full_module_name = full_module_name
        self.content = content

    @staticmethod
    def extract_package_name(full_module_name):
        if not '.' in full_module_name: return full_module_name
        return full_module_name.rsplit('.', 1)[0]

    @staticmethod
    def extract_module_name(full_module_name):
        if not '.' in full_module_name: return '' 
        return full_module_name.rsplit('.', 1)[1]    

    def create(self, root_path):
        package_name = self.extract_package_name(self.full_module_name)
        TestPackage(package_name).create(root_path)
        module_name = self.extract_module_name(self.full_module_name)
        if module_name:
            path = root_path + '/' + package_name.replace('.', '/') + '/'
            module = open(path + module_name + '.py', 'wb')
            print >> module, self.content

class TestBuilder(object):
    JAVA_TEST_BODY = JAVA_TEST_BODY
    JAVA_TEST_TAIL = JAVA_TEST_TAIL
    def __init__(self, suite_name, path = JUNIT_TESTS_PATH):
        self.java_tests = []
        self.suite_name = suite_name
        mkdirs(path)
        mkdirs(suite_name)
        self.java_test_file = open(path + suite_name + ".java", "wt")
        print >>self.java_test_file, JAVA_TEST_HEAD % suite_name
        
    def __del__(self):
	print "Suite", self.suite_name
        for test_name in sorted(self.java_tests):
	    print "Added test:", test_name
            self.java_test_file.write(self.JAVA_TEST_BODY % test_name)
        self.update()
	self.java_test_file.write(self.JAVA_TEST_TAIL)

    def __make_python_test(self, test_name, content):
        path = self.suite_name + "/" + test_name + "/"
        file_name = test_name + '_'+ self.suite_name + '.py'
        mkdirs(path)
        file = open(path + file_name, "wt")
        file.write(content)
    
    def addTest(self, test_name, script_content):
        self.java_tests.append(test_name)
        self.__make_python_test(test_name, script_content)
	
    def update(self):
	update_test_suite(self.suite_name, self)

    def addModularTest(self, test_name, script_content, modules):
        script_path = self.suite_name + "/" + test_name + "/"
        self.addTest(test_name, script_content)
        for module in modules:
            module.create(script_path)

def update_test_suite(suite, builder):
    for test in sorted(os.listdir(suite)):
        python_test_dir = suite+'/'+test+'/'
        if not os.path.isdir(python_test_dir): continue
        oldfile = python_test_dir + OLD_FILE_NAME
        oldfile2 = python_test_dir + suite + '_'+ test + '.py'
        file_name = test + '_'+ suite + '.py'
        newfile = python_test_dir+file_name
        if os.path.exists(oldfile):
            os.rename(oldfile, newfile)
        if os.path.exists(oldfile2):
            os.rename(oldfile2, newfile)
        if os.path.exists(newfile):
            contents = open(newfile, 'rt').read()
        elif os.listdir(python_test_dir): #is there any file or directory?
            contents = ""
        else: # no files -- no test
            continue
        builder.addTest(test, contents)

def update_all_tests():
    for suite in os.listdir("."):
        if not os.path.isdir(suite): continue
        test_builder = TestBuilder(suite)
        update_test_suite(suite, test_builder)

if __name__ == '__main__':
    import sys
    if sys.argv[1:] == ['--all']:
        update_all_tests()
    elif len(sys.argv)==3:
        test_builder = TestBuilder(sys.argv[1])
        test_builder.addTest(sys.argv[2], "")
        del test_builder
        test_builder = TestBuilder(sys.argv[1])
        test_builder.update()
    elif len(sys.argv)==2:
        test_builder = TestBuilder(sys.argv[1])
        test_builder.update()
    else:
        print """Usage: 
        python test_gen.py <suite_name> <test_name> - to add a test
        python test_gen.py --all - to update all java test info from python tests""".replace(' '*8, '\t')