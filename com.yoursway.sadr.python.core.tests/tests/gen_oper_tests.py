from gen_java_test import mkdir
from gen_java_test import gen_test_class 

JAVA_HOME = "src/com/yoursway/sadr/python/core/tests/typeinferencing/"
FILE_NAME = "foo.py"
value_klass = """
class Q(object):pass
"""
bin_klass = """
class Foo(object):
    def %s (self, arg0, arg1):
        return Q()
"""

BINARY_OPERATORS = {
    "__add__": "+",
    "__sub__": "-",
    "__mul__": "*",
    "__floordiv__": "//",
    "__mod__": "%",
    #TODO __divmod__": "divmod",
    "__pow__": "**", #(    self, other[, modulo])
    "__lshift__": "<<",
    "__rshift__": ">>",
    "__and__": "&",
    "__xor__": "^",
    "__or__": "|",
    "__div__": "/",
    "__truediv__": "/",
    "__radd__": "+",
    "__rsub__": "-",
    "__rmul__": "*",
    "__rdiv__": "/",
    "__rtruediv__": "/",
    "__rfloordiv__": "//",
    "__rmod__": "%",
    #TODO __rdivmod__": "",
    "__rpow__": "**",
    "__rlshift__": "<<",
    "__rrshift__": ">>",
    "__rand__": "&",
    "__rxor__": "^",
    "__ror__": "|"
}

UNARY_OPERATORS = {
    "__neg__": "-",
    "__pos__": "+",
    "__invert__": "~"
                   }
ASS_OPERATORS = {
    "__iadd__": "+=",
    "__isub__": "-=",
    "__imul__": "*=",
    "__idiv__": "/=",
    "__itruediv__": "/=",
    "__ifloordiv__": "//=",
    "__imod__": "%=",
    "__ipow__": "**=", #(    self, other[, modulo])
    "__ilshift__": "<<=",
    "__irshift__": ">>=",
    "__iand__": "&=",
    "__ixor__": "^=",
    "__ior__": "|="
}

TEST_BINOP = "x = Foo() %s Foo() ## expr x => Q"
TEST_ASS = "x = Foo()\n x %s Foo() ## expr x => Q"
TEST_UNOP = "x = %sFoo() ## expr x => Q"

def make_java_test(name):
    return """
    @Test
    public void %s() throws Exception {
        runTest();
    }
""" % name

def make_test(test_name, operator, symname, test_str):
    mkdir(test_name + "/" + operator)
    file = open(test_name + "/" + operator + "/" + FILE_NAME, "w")
    print >> file, value_klass, bin_klass % operator
    print >> file, test_str
    
def gen_tests(test_name, operators, test_str):
    java_tests = ''
    for oper, symname in operators.items():
        make_test(test_name, oper, symname, test_str % symname)
        java_tests += make_java_test(oper)
    gen_test_class("../" + JAVA_HOME, test_name, java_tests)

if __name__ == "__main__":
    gen_tests("BinaryOperators", BINARY_OPERATORS, TEST_BINOP)
    gen_tests("UnaryOperators", UNARY_OPERATORS, TEST_UNOP)
    gen_tests("AssignmentOperators", ASS_OPERATORS, TEST_ASS)
