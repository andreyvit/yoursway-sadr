
class Py:
    def __getattr__(self,name):
        return 1
    def __setattr__(self, name, value):
        self.__dict__[name + "_"] = value
    def __delattr__(self, name):
        del self.__dict__[name + "_"]

py = Py()
x = py.calculated ## value x => 1
py.attr = 2
y = py.attr_ ## value y => 2
del py.attr
z = py.attr ## value z => 1




