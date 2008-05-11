
def f(x):
  if x == 5:
    return 1
  else:
    return x + f(x+1)

def bozz():
  a = 1
  b = 5
  z = f(a)
  print z  ## expr z => int
  print z  ## value z => 11


