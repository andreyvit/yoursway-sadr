
class Foo(object):
  def test(self, x):
    puts self.xx ## value self.xx => 42

def ggg():
  name = "idontexist"
  m = """
  class Foo():
    def %s(self):
      self.xx = 42
  """ % name
  eval(m)

