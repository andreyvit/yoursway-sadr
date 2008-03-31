from test_gen import TestBuilder

SCRIPT = """
class Foo(object):
    def __call__(%(all_args)s):
        return %(ret)s
foo = Foo()
x = foo(%(args)s) ## value x => %(check)s"""

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    script_parameters = {"all_args":"self","args":"","ret":"0", "check":"0"}
    builder.addTest("NoArgs",SCRIPT % script_parameters)
    script_parameters = {"all_args":"self, arg0, arg1","args":"1, 2","ret":"arg0", "check":"1"}
    builder.addTest("WithArgs",SCRIPT % script_parameters)

if __name__ == "__main__":
    make_tests("CallableObjectsTests")
