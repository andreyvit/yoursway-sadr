
class Q(object):
	pass


class Foo(object):
    def __neg__ (self):
        return Q()

x = -Foo() ## expr x => Q