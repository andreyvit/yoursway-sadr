class Foo(object):
	def foox(self):
		self.x = 5
		self.z = 5

def foo():
	y = Foo()
	y.foox()
	q = y.z ## value q => 5
	p = y.x	## expr p => int
 
foo()
