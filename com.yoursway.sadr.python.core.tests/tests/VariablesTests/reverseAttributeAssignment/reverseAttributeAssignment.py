
class A(object):
	pass

a = A()	
b = a

def foo():
	b.x = 10
foo()

w = a.x
print w ## value w => 10 
