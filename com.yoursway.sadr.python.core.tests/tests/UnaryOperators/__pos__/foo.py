
class Q(object):pass

class Foo(object):
    def __pos__ (self, arg0, arg1):
        return Q()
x = +Foo() ## expr x => Q
