
class Q(object):pass

class Foo(object):
    def __invert__ (self, arg0, arg1):
        return Q()
x = ~Foo() ## expr x => Q


