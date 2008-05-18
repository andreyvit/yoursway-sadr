class C(object):
    def __init__(self):
        self.q = 0
c = C()
def f(arg):
    arg.q = 1
f(c)
a = c.q ## value a => 1


