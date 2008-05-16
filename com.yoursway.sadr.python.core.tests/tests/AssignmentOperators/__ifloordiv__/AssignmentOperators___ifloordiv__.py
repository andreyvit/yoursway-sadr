
class Q(object):pass

class Foo(object):
    def __ifloordiv__ (lhs, rhs):
        return Q()
x = Foo()
 x //= Foo() ## expr x => Q
