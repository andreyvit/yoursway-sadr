
class Foo:
  pass

def foo():
  x = [1, Foo()]
  return x

def bar():
  zzz = foo() ## expr zzz => Foo[]
  y = zzz[0]
  print y ## expr y => Foo


