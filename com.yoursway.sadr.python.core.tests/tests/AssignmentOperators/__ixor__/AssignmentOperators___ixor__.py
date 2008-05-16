
class Q(object):pass

class Foo(object):
    def __ixor__ (lhs, rhs):
        return Q()
x = Foo()
 x ^= Foo() ## expr x => Q
