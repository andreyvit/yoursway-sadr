
class Foo:
  def test(x):
    puts self.xx ## value self.xx => 42

def ggg():
  name = "idontexist"
  m1 = "class Foo; def " + name
  m2 = m1 + "<CR>  @xx = 42<CR>end<CR>end<CR>"
  eval(m2)

