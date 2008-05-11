
class Foo:
  pass

def foo():
  x = []
  x[1] = Foo()
  return x

def bar():
  zzz = foo() ## expr zzz => Foo[]
  y = zzz[1]
  print y ## expr y => Foo


