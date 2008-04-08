
class Foo():
    @classmethod
    def f(cls):
        return cls
    @staticmethod
    def q(arg):
        return arg

a = foo.q(0) ## value a => 0
