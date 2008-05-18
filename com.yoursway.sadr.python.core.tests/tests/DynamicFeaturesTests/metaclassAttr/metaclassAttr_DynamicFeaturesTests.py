
class Meta(type):
    def __init__(cls, name, bases, dict):
        cls.attr = 0
class C(object):
    __metaclass__ = Meta

x = C.attr ## value x => 0



