
def foo():
	w = 42
	def bar():
		global w
		w = "bar"
		print w ## value w => 'bar'
	print w ## value w => 42
