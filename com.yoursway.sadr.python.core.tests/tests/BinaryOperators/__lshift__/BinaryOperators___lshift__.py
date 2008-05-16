
class Q(object):pass

class Foo(object):
    def __lshift__ (lhs, rhs):
        return Q()
x = Foo() << Foo() ## expr x => Q
