
class Foo:
  def foo(self):
    return 10

class Bar(Foo):
  def foo(self):
    return "test"

def boz():
  f = Bar.new()
  z = f.foo()
  print z ## expr z => String
