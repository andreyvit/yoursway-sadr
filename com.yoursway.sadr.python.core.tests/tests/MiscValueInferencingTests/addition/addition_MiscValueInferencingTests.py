
class Foo:
  def foo(self):
    return 10
	
  def bar(self):
    x = self.foo()
    y = 20
    z = x + y
    print z ## value z => 30

def bozz():
  x = "abc"
  y = "def"
  z = x + y
  print z ## value z => "abcdef"


