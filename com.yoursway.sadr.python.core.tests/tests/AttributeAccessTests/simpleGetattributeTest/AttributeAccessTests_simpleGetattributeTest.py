
class Py(object):
    def __getattribute__(self, name):
        return 0
x = Py().any_attribute ## value x => 0


