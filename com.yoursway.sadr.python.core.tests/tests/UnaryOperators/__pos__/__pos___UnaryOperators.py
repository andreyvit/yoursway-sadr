
class Q(object):
	pass


class Foo(object):
    def __pos__ (self):
        return Q()

x = +Foo() ## expr x => Q