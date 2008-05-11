
def foo(u):
  if u:
    return 10
  else:
    return u + foo(u)

def bozz():
  z = foo(0)
  print z   ## value z => none

