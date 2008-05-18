
class Q(object):
	pass


class Foo(object):
    def __ifloordiv__ (self, rhs):
        return Q()

x = Foo()
x //= Foo() ## expr x => Q