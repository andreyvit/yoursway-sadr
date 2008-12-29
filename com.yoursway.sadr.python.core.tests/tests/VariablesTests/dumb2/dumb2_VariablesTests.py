#class Foo(object):
#	def __init__(self, x):
#		self.a = x
#		
#class Bar(Foo):
#	def __init__(self, buzz):
#		Foo.__init__(self, buzz)
#		self.y = self.a

class Foo():
    pass

a.x = Foo()
w = a.x
print w ## expr w => Foo 


