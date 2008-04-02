from test_gen import TestBuilder, TestModule

EMPTY_CONTAINERS = """
e_dict = {}    ## expr e_dict => dict
e_list = []    ## expr e_list => list 
e_tuple = ()   ## expr e_tuple => tuple 
e_set = set()  ## expr e_set => set 
e_frozenset = frozenset() ## expr e_frozenset => frozenset
"""

NUMERIC_TYPES = """
i = 0     ## expr i => int
l0 = 0L   ## expr l0 => long
l1 = 0l   ## expr l1 => long
c = 1j    ## expr c => complex
f = 1.0   ## expr f => float
b = True  ## expr b => bool
"""

STRING_TYPES = """
s = ""    ## expr s => str
u0 = u""   ## expr u0 => unicode
u1 = U""   ## expr u1 => unicode
"""

MODULE_TEST = """
import bar
x = bar ## expr bar => module
"""

TYPES_TESTS = """
o = object() ## expr o => object
t = type     ## expr t => type
"""

MISC_TYPES = """
none = None ## expr none => NoneType
sl = slice(0,0,None) ## expr sl => slice
nimpl = NotImplemented ## expr nimpl => NotImplementedType 
"""

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    builder.addTest("containerRecognition", EMPTY_CONTAINERS)
    builder.addTest("numericTypes", NUMERIC_TYPES)
    builder.addTest("stringTypes", STRING_TYPES)
    builder.addTest("typeTests", TYPES_TESTS)
    builder.addTest("miscTypes", MISC_TYPES)

    builder.addModularTest("module", MODULE_TEST,[TestModule("bar","")])
    
if __name__ == '__main__':
    make_tests("BuiltInTypes")
