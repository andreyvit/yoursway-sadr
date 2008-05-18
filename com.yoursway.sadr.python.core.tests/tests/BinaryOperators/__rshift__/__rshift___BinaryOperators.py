
class Q(object):
	pass


class Foo(object):
    def __rshift__ (self, rhs):
        return Q()

x = Foo() >> Foo() ## expr x => Q