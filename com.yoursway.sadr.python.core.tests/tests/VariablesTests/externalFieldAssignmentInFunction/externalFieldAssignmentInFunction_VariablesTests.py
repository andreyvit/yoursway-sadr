
class Foo:
  pass
  
def func(p):
  p.xxx = 42
  
def boo():
  f = Foo()
  func(f)
  y = f.xxx ## expr y => int



