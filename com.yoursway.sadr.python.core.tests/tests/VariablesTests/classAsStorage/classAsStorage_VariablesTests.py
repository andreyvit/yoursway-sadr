class Foo(object):
	pass
		
def foo():
	Foo.a = 5
	p = Foo.a ## expr p => int
	q = Foo.a ## value q => 5


