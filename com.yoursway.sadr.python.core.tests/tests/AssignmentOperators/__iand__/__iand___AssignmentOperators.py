
class Q(object):
	pass


class Foo(object):
    def __iand__ (self, rhs):
        return Q()

x = Foo()
x &= Foo() ## expr x => Q