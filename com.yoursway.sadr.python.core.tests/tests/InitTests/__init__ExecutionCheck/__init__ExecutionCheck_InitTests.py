# a simple variant of VariablesTests.subclassing 
class C(object):
    def __init__(self):
        self.q = 0
class D(object):
    def __init__(self):
        C.__init__(self)
        self.z = self.q

a = C().z ## value a => 0


