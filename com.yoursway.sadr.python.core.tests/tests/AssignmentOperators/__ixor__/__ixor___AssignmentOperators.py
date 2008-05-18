
class Q(object):
	pass


class Foo(object):
    def __ixor__ (self, rhs):
        return Q()

x = Foo()
x ^= Foo() ## expr x => Q