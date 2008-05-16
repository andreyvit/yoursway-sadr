
class Q(object):pass

class Foo(object):
    def __rmod__ (lhs, rhs):
        return Q()
x = Foo() % Foo() ## expr x => Q
