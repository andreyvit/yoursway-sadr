
class Foo:
  pass

def foo():
  x = [1, Foo()]
  return x

def bar():
  zzz = foo() ## expr zzz => list
  y = zzz[1]
  print y ## expr y => Foo


