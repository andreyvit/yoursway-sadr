from test_gen import TestBuilder

NEW_STYLE_CLASS = """
class Py(object):
    def __getattr__(self,name):
        return 1
    def __setattr__(self, name, value):
        object.__setattr__(self, name + "_", value)
    def __delattr__(self, name):
        object.__delattr__(self, name + "_")
"""

OLD_STYLE_CLASS = """
class Py:
    def __getattr__(self,name):
        return 1
    def __setattr__(self, name, value):
        self.__dict__[name + "_"] = value
    def __delattr__(self, name):
        del self.__dict__[name + "_"]
"""

TESTS = """
py = Py()
x = py.calculated ## value x => 1
py.attr = 2
y = py.attr_ ## value y => 2
del py.attr
z = py.attr ## value z => 1

"""
SIMPLE_GETATTRIBUTE_TEST = """
class Py(object):
    def __getattribute__(self, name):
        return 0
x = Py().any_attribute ## value x => 0
"""

COMPLEX_GETATTRIBUTE_TEST = """
class Py(object):
    def __getattribute__(self, name):
        if name == "q":
            return 0
        else:
            return name
x = Py().q ## value x => 0
y = Py().z ## value y => "z"
"""

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    builder.addTest("attrAccsessNewStyle", NEW_STYLE_CLASS + TESTS)
    builder.addTest("attrAccessOldStyle", OLD_STYLE_CLASS + TESTS)
    builder.addTest("simpleGetattributeTest", SIMPLE_GETATTRIBUTE_TEST)
    builder.addTest("complexGetattributeTest", COMPLEX_GETATTRIBUTE_TEST)

if __name__ == '__main__':
    make_tests("AttributeAccessTests")