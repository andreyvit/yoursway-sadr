from test_gen import TestBuilder

SCRIPT = """
a = %s
b = %s
"""

def gen(a, b, ops):
    r = SCRIPT % (repr(a), repr(b))
    for op in ops:
	try:
	    c = eval(op, {'a':a, 'b': b})
	except:
	    continue
	r += "c = %s ## value c => %s\n" % (op, repr(c))
    return r

def make_tests(suite_name):
    builder = TestBuilder(suite_name)
    ops = ['a+b', 'a-b', 'a*b', 'a/b', 'a&b', 'a^b', 'a%b', '-a+b', 'int(a)', 'str(int(a))', 'long(a)', 'float(a)']
    builder.addTest("intTest", gen(2, 3, ops))
    builder.addTest("intTest2", gen(100, 5, ops))
    builder.addTest("longTest", gen(2, 3L, ops))
    builder.addTest("longTest2", gen(100, 5L, ops))
    builder.addTest("floatTest", gen(2.1, 3.2, ops))
    builder.addTest("floatTest2", gen(100.87, 5, ops))
    builder.addTest("floatTest3", gen(5536L, 1e7+1e3+1e-2, ops))
    builder.addTest("boolTest", gen(True, False, ops))
    builder.addTest("boolTest2", gen(False, True, ops))
    ops = ['a+b', 'a-b', 'a*b', 'a/b', 'a&b', 'a^b', 'a%b', '-a+b', 'int(a)', 'str(int(a))', 'chr(35)']
    builder.addTest("strTest", gen('sadfv', 3, ops))
    builder.addTest("strTest2", gen('asdf', 'yoklmn', ops))
    ops = ['a+b', 'a-b', 'a*b', 'a/b', 'a&b', 'a^b', 'a%b', '-a+b', 'int(a)', 'unichr(1049)']
    builder.addTest("uniTest", gen(u'sadfv', 3, ops))
    builder.addTest("uniTest2", gen(u'vdf', u'hf', ops))
    builder.addTest("uniTest3", gen('asdf', u'yoprst', ops))

if __name__ == "__main__":
    make_tests("ValueCalculationTests")
