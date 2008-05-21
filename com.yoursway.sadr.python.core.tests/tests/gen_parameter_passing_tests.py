from test_gen import TestBuilder

TESTS = ["""
def f(a, b):
    return a+b

r = f(1,2) ## value r => 3
""",
"""
def f(*args):
    return args[1]
r = f(*(1,2)) ## value r => 2
""", 
"""
def f(*args):
    return args[1]
r = f(1,2) ## value r => 2
""", 
"""
def f(a, b=5, *args):
    return a+b+len(args)
r = f(1,2) ## value r => 3
""", 
"""
def f(a, b=5, c=3):
    return a+b+c
r = f(1,c=2) ## value r => 8
""", 
"""
def f(**args):
    return args['py'] + args['q']
r = f(py = 2, q = 1) ## value r => 3
""",
"""
def f(*s_args, **kw_args):
    return s_args[0] + kw_args['py']
r = f(2, py = 1) ## value r => 3
""",
"""
def f(p_arg, *s_args, **kw_args):
    return (s_args[0] + kw_args['py'])*p_arg
r = f(0, 2, py = 1) ## value r => 3
""",
"""
def f(*args):
    return args[0]
r = f(*[1,2,3]) ## value r => 1
""",
"""
def f(**args):
    return args['py']
r = f(**{'py':0}) ## value r => 0
"""
]

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    ind = 0
    for test in TESTS:
        builder.addTest("paramPass"+str(ind), test)
        ind += 1

if __name__ == '__main__':
    make_tests("ParametersPassing")
