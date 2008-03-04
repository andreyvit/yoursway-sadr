
def bozz():
  x = 1 
  print x   ## not-cached localvar-type x
  print x   ## expr x => int
  print x   ## cached localvar-type x
