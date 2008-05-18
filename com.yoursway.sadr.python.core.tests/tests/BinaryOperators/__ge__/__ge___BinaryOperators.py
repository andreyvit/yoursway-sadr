
class Q(object):
	pass


class Foo(object):
    def __ge__ (self, rhs):
        return Q()

x = Foo() >= Foo() ## expr x => Q