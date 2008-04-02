from test_gen import TestBuilder

DECORATOR_TEST_HEAD = """
def decorator(fn):
    def decoration(arg):
        return fn(arg) + 1
    return decoration"""

DECORATOR_TESTS = [ """
@decorator
def func(arg):
    return arg

x = func(0) ## value x => 1 
""", """
@decorator
@decorator
def func(arg):
    return arg

x = func(0) ## value x => 2 
""" ]


CLASS_METHOD_DECORATOR_TESTS = """
class Foo():
    @classmethod
    def f(cls):
        return cls
    @staticmethod
    def q(arg):
        return arg
"""
TESTS = ["""
foo = Foo()""", """"
c = foo.f() ## value c => Foo""", """
c1 = Foo.f() ## value c1 => Foo""", """
a = foo.q(0) ## value a => 0""", """
a = Foo.q(0) ## value a => 0""" ]

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    ind = 0
    for test in DECORATOR_TESTS:
        builder.addTest("decorator"+str(ind), DECORATOR_TEST_HEAD + test);
        ind += 1
    ind = 0
    for test in TESTS:
        builder.addTest("classMethodBuiltinDecorators"+str(ind), CLASS_METHOD_DECORATOR_TESTS + test)
        ind += 1

if __name__ == '__main__':
    make_tests("DecoratorsTests")
