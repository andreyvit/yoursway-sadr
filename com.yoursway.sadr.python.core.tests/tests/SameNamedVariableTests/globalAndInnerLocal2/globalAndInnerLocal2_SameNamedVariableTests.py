
w = 24
def foo():
	global w
	w = 42
	def bar():
		w = "bar"
		print w ## value w => 'bar'
	print w ## value w => 24,42
