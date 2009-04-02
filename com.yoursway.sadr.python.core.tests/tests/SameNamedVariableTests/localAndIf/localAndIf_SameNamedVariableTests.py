
def foo():
	w = 42
	if True:
		w = "bar"
		print w ## value w => 'bar',42
	print w ## value w => 'bar',42
