
class Foo:
  pass

def define_foo(n):
  eval("class Foo; def foo" + i + "<CR>end<CR>end<CR>")

define_foo(2)
