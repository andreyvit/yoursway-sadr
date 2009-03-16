
class Q(object):
	pass


class Foo(object):
    def __iadd__ (self, rhs):
        print rhs

x = Q()
x += Foo() ## expr x => Foo