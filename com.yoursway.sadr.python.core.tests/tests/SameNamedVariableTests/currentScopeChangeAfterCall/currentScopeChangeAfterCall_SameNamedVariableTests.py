
def foo(a):
	global b
	b = a

a = 42
foo(10)
print b ## value b => 10
