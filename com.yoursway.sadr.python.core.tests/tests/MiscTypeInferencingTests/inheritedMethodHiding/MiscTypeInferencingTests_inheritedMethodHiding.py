
class Foo:
  def foo(self):
    return 10

class Bar(Foo):
  def foo(self):
    return 'test'

def boz():
  f = Bar()
  z = f.foo()
  print z ## expr z => str

