
class bar(list):
	def foo(self, y):
		self.append(y)
	
a = bar()
a.foo(10)
print a ## value a => [10]
