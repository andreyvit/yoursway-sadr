
class Foo:
  def boo(self):
    x = Foo.new()
    print x ## has-method x foo8

def define_foo(n):
  eval("class Foo; def foo" + n + "; end; end")

for i in range(1, 10): 
  define_foo(i)
