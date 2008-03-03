
def bar(x, y):
  return y

def bozz():
  a = 1
  b = 2
  z = bar(a, b)
  print z ## expr z => Fixnum
  w = bar("a", "b")
  print w ## expr w => String
