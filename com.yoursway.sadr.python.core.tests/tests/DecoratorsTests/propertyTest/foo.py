
class C(object):
    @property
    def attr(self):
        return 0
x = C().attr ## value x => 0
y = C.attr ## expr y => property

