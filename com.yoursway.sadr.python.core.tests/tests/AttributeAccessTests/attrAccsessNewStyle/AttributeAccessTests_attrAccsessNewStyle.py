
class Py(object):
    def __getattr__(self,name):
        return 1
    def __setattr__(self, name, value):
        object.__setattr__(self, name + "_", value)
    def __delattr__(self, name):
        object.__delattr__(self, name + "_")

py = Py()
x = py.calculated ## value x => 1
py.attr = 2
y = py.attr_ ## value y => 2
del py.attr
z = py.attr ## value z => 1



