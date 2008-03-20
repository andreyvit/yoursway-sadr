# no arguments for construction
class Q(object):pass
class C(object):
    def __new__(cls):
        return Q();
q = C() ## expr q => Q