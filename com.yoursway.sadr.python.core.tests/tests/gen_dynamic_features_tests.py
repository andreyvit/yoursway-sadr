from test_gen import TestBuilder

TESTS = {
"functionObjectFlows":"""
def f():
    return 0
q = f 
x = q() ## value x => 0
""",
"closure":"""
var = 0
def f():
    var = 1
    def q():
        return var
    return q
qq = f()
x = qq()    ## value x => 1
""",
"instanceAttributeFromFunction":"""
def method():return 0
class C(object):pass
c = C()
c.m = method
x = c.m() ## value x => 0 
""",
"instanceMethodFromFunction":
"""
def method(self):return self.a
class C(object):
    a = 1
C.m = method
x = c.m() ## value x => 1
""",
"calculatedSuperClasse":"""
class C(object):
    a = 0
klass = C
class D(klass):pass
x = D().a ## value x => 0
""",
"metaclassAttr": """
class Meta(type):
    def __init__(cls, name, bases, dict):
        cls.attr = 0
class C(object):
    __metaclass__ = Meta

x = C.attr ## value x => 0
"""
}

def make_test(suite_name):
    builder = TestBuilder(suite_name);
    for name, content in TESTS.items():
        builder.addTest(name,content)

if __name__ == '__main__':
    make_test("DynamicFeaturesTests")
