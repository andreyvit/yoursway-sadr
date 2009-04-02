
def foo():
	w = 42
	def bar():
		w = "boz"
		print w ## value w => 'boz'
	print w ## value w => 42
