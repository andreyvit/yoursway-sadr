"""Type conversion operations tests"""

from test_gen import TestBuilder
METHS_TO_FUNCS = {
                  "__str__":"str",
                  "__nonzero__":"bool",
                  "__len__":"bool",
                  "__int__":"int"
                  }
METHS_TO_VALS = {
                  "__str__":"'string'",
                  "__nonzero__":"True",
                  "__len__":"1",
                  "__int__":"2"
                  }

SCRIPT = """
class Foo(object):
    def %(meth)s (self):
        return %(val)s
x = %(func)s(Foo()) ## value x => %(val)s
"""

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    for meth, func in METHS_TO_FUNCS.items():
        script = SCRIPT % {'meth':meth, 'val':METHS_TO_VALS[meth], 'func':func}
        builder.addTest(meth,script)

if __name__=="__main__":
    make_tests("TypeConversion")
