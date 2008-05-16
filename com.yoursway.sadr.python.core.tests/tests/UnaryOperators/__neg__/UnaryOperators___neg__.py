
class Q(object):pass

class Foo(object):
    def __neg__ (lhs, rhs):
        return Q()
x = -Foo() ## expr x => Q
