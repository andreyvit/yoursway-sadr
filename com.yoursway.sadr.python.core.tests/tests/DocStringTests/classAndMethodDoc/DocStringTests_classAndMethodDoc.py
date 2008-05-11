
class C(object):
    "class doc"
    def f(self):
        "method doc"
x = C.__doc__ ## value x => "class doc"
y = C.f.__doc__ ## value y => "method doc"



