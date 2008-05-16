
class Q(object):pass

class Foo(object):
    def __pos__ (lhs, rhs):
        return Q()
x = +Foo() ## expr x => Q
