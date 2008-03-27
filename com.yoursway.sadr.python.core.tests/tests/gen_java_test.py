import os

JAVA_TEST_HEAD = """package com.yoursway.sadr.python.core.tests.typeinferencing;

import org.junit.Test;

public final class %s extends AbstractTypeInferencingTestCase {
"""
JAVA_TEST_TAIL = "}"

def mkdir(dir):
    if not os.path.exists(dir):
        os.mkdir(dir)

def gen_test_class(path, test_class_name, tests):
    mkdir(path)
    java_test_file = open(path + test_class_name+".java", "w")
    print >>java_test_file, JAVA_TEST_HEAD % test_class_name,
    print >>java_test_file, tests,
    print >>java_test_file, JAVA_TEST_TAIL 
