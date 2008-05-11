
class Foo:
  def boo(self):
    x = Foo()
    print x ## has-method x foo8

def define_foo(n):
  eval("""
  class Foo: 
    def foo""" + n + """(self):
      pass
""")

for i in range(1, 11): 
  define_foo(i)


