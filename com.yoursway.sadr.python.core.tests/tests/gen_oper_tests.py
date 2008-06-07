from test_gen import TestBuilder
value_klass = """
class Q(object):
	pass

"""
binklass = """
class Foo(object):
    def %s (self, rhs):
        return Q()

"""

unklass = """
class Foo(object):
    def %s (self):
        return Q()

"""

assklass = """
class Foo(object):
    def %s (self, rhs):
        print rhs

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
#   "__truediv__": "/",
    "__radd__": "+",
    "__rsub__": "-",
    "__rmul__": "*",
    "__rdiv__": "/",
#   "__rtruediv__": "/",
    "__rfloordiv__": "//",
    "__rmod__": "%",
    #TODO __rdivmod__": "",
    "__rpow__": "**",
    "__rlshift__": "<<",
    "__rrshift__": ">>",
    "__rand__": "&",
    "__rxor__": "^",
    "__ror__": "|",
    "__lt__": "<", 
    "__le__": "<=",
    "__eq__": "==",
    "__ne__": "!=",
    "__gt__": ">",
    "__ge__": ">="
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
TEST_ASS = "x = Foo()\nx %s Foo() ## expr x => Foo"
TEST_UNOP = "x = %sFoo() ## expr x => Q"

def gen_tests(suite_name, operators, test_str, klass):
    builder = TestBuilder(suite_name)
    for oper, symname in operators.items():
        script_content = value_klass + klass % oper + test_str % symname
        builder.addTest(oper, script_content)

if __name__ == "__main__":
    gen_tests("BinaryOperators", BINARY_OPERATORS, TEST_BINOP, binklass)
    gen_tests("UnaryOperators", UNARY_OPERATORS, TEST_UNOP, unklass)
    gen_tests("AssignmentOperators", ASS_OPERATORS, TEST_ASS, assklass)
