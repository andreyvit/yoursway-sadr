
class Foo(object):
    def __call__(self, arg0, arg1):
        return arg0
foo = Foo()
x = foo(1, 2) ## value x => 1


