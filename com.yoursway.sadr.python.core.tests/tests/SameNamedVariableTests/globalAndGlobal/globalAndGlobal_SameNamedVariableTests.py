
w = 42

def foo():
	global w
	w = "bar"
	print w ## value w => 'bar',42

print w ## value w => 'bar',42
