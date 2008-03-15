
class Foo:
  pass

def define_foo(n):
  eval("""
  class Foo:
    def foo""" + i + "(self): pass")

define_foo(2)
