
def bar(x, y):
  return y 

def bozz():
  a = 1
  b = 2
  z = bar(a, b)
  print a	## not-cached localvar-type a
  print b	## not-cached localvar-type b
  print z	## expr z => int
  print a	## not-cached localvar-type a
  print b	## cached localvar-type b

