
class Foo(object):
  def test(self, x):
    q = self.xx ## value q => 42

def ggg():
  name = "idontexist"
  m = """
  class Foo():
    def %s(self):
      self.xx = 42
  """ % name
  eval(m)


