
def foo(u, v):
  if blah:
    return bar(u, v)
  else:
    return v

def bar(x, y):
  if blah:
    return foo(y, x)
  else:
    return 42

def bozz():
  a = 1
  b = 2
  z = bar(a, b)
  print z   ## expr z => int
  w = bar("a", "b")
  print w   ## expr w => int,str

