
class Foo():
    @classmethod
    def f(cls):
        return cls
    @staticmethod
    def q(arg):
        return arg

c1 = Foo.f() ## value c1 => Foo


