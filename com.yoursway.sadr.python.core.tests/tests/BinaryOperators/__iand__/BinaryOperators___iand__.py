
class Q(object):pass

class Foo(object):
    def __iand__ (self, arg0, arg1):
        return Q()

x = Foo()
 x &= Foo() ## expr x => Q

