
def userx(x,y):
  return x

def usery(x,y):
  return y

def bar(x, y, z):
  return userx(x,y) + usery(x,y)

def bozz():
  a = 1
  b = 2
  z = bar(a, b, a)
  print z   ## expr z => int
  w = bar("a", "b", b)
  print w   ## expr w => str

def boz2():
  z = bar("a", "b", 0)
  print z   ## expr z => str

