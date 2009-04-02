
class foo(object):
	w = 42
	if True:
		w = "bar"
		print w ## value w => 42,"bar"
	print w ## value w => 42,"bar"
