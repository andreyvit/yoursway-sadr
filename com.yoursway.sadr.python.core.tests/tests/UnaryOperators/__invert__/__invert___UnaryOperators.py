
class Q(object):
	pass


class Foo(object):
    def __invert__ (self):
        return Q()

x = ~Foo() ## expr x => Q