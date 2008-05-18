
class Py(object):
    def __getattribute__(self, name):
        if name == "q":
            return 0
        else:
            return name
x = Py().q ## value x => 0
y = Py().z ## value y => "z"



