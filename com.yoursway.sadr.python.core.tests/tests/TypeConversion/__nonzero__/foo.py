
class Foo(object):
    def __nonzero__ (self):
        return True
x = bool(Foo()) ## value x => True

