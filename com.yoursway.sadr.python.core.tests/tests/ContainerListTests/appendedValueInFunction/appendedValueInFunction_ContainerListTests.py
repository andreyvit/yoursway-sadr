
def foo(x, y):
	x.append(y)
	
a = [42]
foo(a, 10)
print a ## value a => [10, 42]
