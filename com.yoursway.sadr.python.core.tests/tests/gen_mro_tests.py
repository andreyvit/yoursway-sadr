from test_gen import TestBuilder

TEST_HEAD = """
class Py(object):
    def f(self):
        return 2

class A(Py): 
    def f(self):
        return 0
class B(Py):
    def f(self):
        return 1
"""
EXPLICIT_TEST = """
class C(A,B):pass
x = C().f() ## value x => 0
class D(B,A):pass
y = D().f() ## value y => 1
"""

MRO_ATTR_TEST = """
class C(A,B):pass
x = C.__mro__ ## value x => [C, A, B, Py, object]
"""

def make_test(suite_name):
    builder = TestBuilder(suite_name)
    builder.addTest("explicitMROTest", TEST_HEAD + EXPLICIT_TEST)
    builder.addTest("mroAttrTest", TEST_HEAD + MRO_ATTR_TEST)

if __name__ == '__main__':
    make_test("MethodResolutionOrder")
