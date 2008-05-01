from test_gen import TestBuilder

TEST = """a = 0
print a ## find-var a 1
"""

def make_test(suite_name):
    builder = TestBuilder(suite_name)
    builder.addTest("simpleTest",TEST);

if __name__ == '__main__':
    make_test("FindVarTests")
