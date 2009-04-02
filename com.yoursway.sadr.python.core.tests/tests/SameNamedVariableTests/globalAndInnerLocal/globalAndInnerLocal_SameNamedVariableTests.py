
w = 6
def foo():
	global w
	def bar():
		w = "bar"
		print w ## value w => 'bar'
	print w ## value w => 6
