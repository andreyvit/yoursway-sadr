
class Q(object):pass

class Foo(object):
    def __le__ (self, arg0, arg1):
        return Q()
x = Foo() <= Foo() ## expr x => Q

