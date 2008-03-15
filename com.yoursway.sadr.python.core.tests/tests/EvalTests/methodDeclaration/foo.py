
class Foo:
  def test(self, x):
    x = self.idontexist()
    print x ## expr x => Fixnum

def ggg():
  m = """
  class Foo():
    def %s(self):
      self.xx = 10
  """ % name
  eval(m)
