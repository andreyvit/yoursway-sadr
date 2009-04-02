
w = "boz"
def foo():
	w = 42
	def bar():
		global w
		print w ## value w => 'boz'
	print w ## value w => 42
