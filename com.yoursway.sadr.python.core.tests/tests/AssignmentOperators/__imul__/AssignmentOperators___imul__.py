
class Q(object):pass

class Foo(object):
    def __imul__ (lhs, rhs):
        return Q()
x = Foo()
 x *= Foo() ## expr x => Q
