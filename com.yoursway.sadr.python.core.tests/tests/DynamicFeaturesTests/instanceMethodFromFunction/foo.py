
def method(self):return self.a
class C(object):
    a = 1
C.m = method
x = c.m() ## value x => 1

