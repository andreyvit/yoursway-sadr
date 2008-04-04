from test_gen import TestBuilder

SIMPLE_LAMBDA_CALC = """
x = (lambda : 1)() ## value x => 1
"""

LAMBDA_FLOWS_TEST = """
x = lambda : 1
y = x() ## value y => 1
"""

COMPLEX_LAMBDA_TEST = """
x = lambda x, y : x + y
y = x(1,2) ## value y => 3
"""

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    builder.addTest("simpleLambdaCalc", SIMPLE_LAMBDA_CALC)
    builder.addTest("lamdaFlowsTest", LAMBDA_FLOWS_TEST)
    builder.addTest("parametrizedLambdaFlows", COMPLEX_LAMBDA_TEST)
    
if __name__ == '__main__':
    make_tests("LambdaExpressionTests")
