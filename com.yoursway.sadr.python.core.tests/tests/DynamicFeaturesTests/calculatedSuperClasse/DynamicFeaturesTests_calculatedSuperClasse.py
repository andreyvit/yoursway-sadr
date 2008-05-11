
class C(object):
    a = 0
klass = C
class D(klass):pass
x = D().a ## value x => 0



