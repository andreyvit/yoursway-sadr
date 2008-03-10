
def bozz():
  x = 1 
  print x   ## not-cached localvar-type x
  print x   ## expr x => Fixnum
  print x   ## cached localvar-type x

