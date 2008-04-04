
class Foo():
    @classmethod
    def f(cls):
        return cls
    @staticmethod
    def q(arg):
        return arg

foo = Foo()
