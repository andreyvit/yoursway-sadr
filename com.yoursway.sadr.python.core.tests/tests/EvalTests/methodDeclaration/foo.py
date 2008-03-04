
class Foo:
  def test(x):
    x = self.idontexist()
    print x ## expr x => Fixnum

def ggg():
  name = "idontexist"
  m1 = "class Foo; def " + name
  m2 = m1 + "<CR> return 10<CR>end<CR>end<CR>"
  eval(m2)
