
class Foo(object):
	def __init__(self, x):
		self.a = x
		
class Bar(Foo):
	def __init__(self, y):
		Foo.__init__(self, 5)
		self.y = a

def foo():
	q = Bar().y ## expr q => int
	print q ## value q => 5
	w = Bar().a ## expr w => int
	print w ## value w => 5
 
		