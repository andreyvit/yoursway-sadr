
class Q(object):
	pass


class Foo(object):
    def __imul__ (self, rhs):
        return Q()

x = Foo()
x *= Foo() ## expr x => Q