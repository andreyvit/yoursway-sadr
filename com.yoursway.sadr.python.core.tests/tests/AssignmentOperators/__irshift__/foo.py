
class Q(object):pass

class Foo(object):
    def __irshift__ (self, arg0, arg1):
        return Q()
x = Foo()
 x >>= Foo() ## expr x => Q
