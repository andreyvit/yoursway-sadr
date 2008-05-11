
def method():return 0
class C(object):pass
c = C()
c.m = method
x = c.m() ## value x => 0 


