
class Q(object):
	pass


class Foo(object):
    def __ior__ (self, rhs):
        return Q()

x = Foo()
x |= Foo() ## expr x => Q