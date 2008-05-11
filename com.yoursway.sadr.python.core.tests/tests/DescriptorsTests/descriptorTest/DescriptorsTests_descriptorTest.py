
class descr(object):
    def __get__(self, inst, owner):
        return inst.q
    def __set__(self, inst, val):
        inst.q = val
    def __delete__(self, inst):
        inst.q = None
class C(object):
    def __init__(self):
        self.q = 0
    d = descr()
c = C()
x = c.q ## value x => 0
y = c.d ## value y => 0
c.d = 1
z = c.d ## value z => 1
del c.d
q = c.d ## value qq => None



