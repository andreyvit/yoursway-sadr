
class Py(object):
    def f(self):
        return 2

class A(Py): 
    def f(self):
        return 0
class B(Py):
    def f(self):
        return 1

class C(A,B):pass
x = C.__mro__ ## value x => [C, A, B, Py, object]



