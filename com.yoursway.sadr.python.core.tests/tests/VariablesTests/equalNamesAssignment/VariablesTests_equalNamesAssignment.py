class Foo(object):
	def foo(self, x):
		self.x = x
		
def foo():
	y = Foo()
	y.foo(5)
	p = y.x ## expr p => int
	q = y.x ## value q => 5
 
		

