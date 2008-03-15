
def bar(x, y):
  x = x
  y = y + x
  return 14

def bozz():
  a = 1
  b = 2
  z = bar(a, b)
  print z   ## expr z => int
  w = bar("a", "b")
  print w   ## expr w => int
