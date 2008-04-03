from test_gen import TestBuilder
TESTS={
"moduleDoc":"""
"module doc"
x = __doc__ ## value x => "module doc" 
""",
"functionDoc":"""
def f():
    "function doc"
x = f.__doc__ ## value x => "function doc" 
""",
"classAndMethodDoc": """
class C(object):
    "class doc"
    def f(self):
        "method doc"
x = C.__doc__ ## value x => "class doc"
y = C.f.__doc__ ## value y => "method doc"
"""
}
def make_test(suite_name):
    builder = TestBuilder(suite_name)
    for name, test in TESTS.items():
        builder.addTest(name,test)

if __name__ == '__main__':
    make_test("DocStringTests")
