
def foo(u, v):
  return v

def bar(x, y):
  return foo(y, x) 

def bozz():
  a = 1
  b = 2
  z = bar(a, b)
  print z   ## expr z => int
  w = bar("a", "b")
  print w   ## expr w => str

