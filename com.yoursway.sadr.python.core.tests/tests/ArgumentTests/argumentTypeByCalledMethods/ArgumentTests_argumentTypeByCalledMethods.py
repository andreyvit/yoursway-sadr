
class Foo:
  def foo(self):
    pass

class Bar:
  def boz(self, x):
    x.foo()
    print x ## expr x => Foo

def gboz(x):
  x.foo()
  print x ## expr x => Foo



