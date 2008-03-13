
class Foo:
  def foo():
    pass

class Bar:
  def boz(x):
    x.foo()
    print x ## expr x => Foo

def gboz(x):
  x.foo()
  print x ## expr x => Foo

