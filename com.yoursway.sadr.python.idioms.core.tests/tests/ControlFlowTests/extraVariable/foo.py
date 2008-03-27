
def bozz():
  x = 42 ## idiom no extra-variable
  x = y + z  ## idiom extra-variable
  return x

def bozz():
  x = 42 ## idiom no extra-variable
  x = y + z  ## idiom no extra-variable
  y = 5 ## idiom no extra-variable
  return x

