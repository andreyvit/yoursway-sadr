
class Q(object):
	pass


class Foo(object):
    def __isub__ (self, rhs):
        print rhs

x = Foo()
x -= Foo() ## expr x => Foo