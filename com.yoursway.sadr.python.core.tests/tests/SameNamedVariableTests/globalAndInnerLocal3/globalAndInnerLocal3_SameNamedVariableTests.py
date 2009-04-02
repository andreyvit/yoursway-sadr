
def foo():
	global w
	w = 42
	def bar():
		w = "bar"
		print w ## value w => 'bar'
	print w ## value w => 42
