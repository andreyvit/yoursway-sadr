class Foo(object):
	def __init__(self, x):
		self.a = x
		
class Bar(Foo):
	def __init__(self, buzz):
		Foo.__init__(self, buzz)
		self.y = self.a

def foo():
	q = Bar(5).y ## expr q => int
	print q ## value q => 5
	w = Bar(5).a ## expr w => int
	print w ## value w => 5
