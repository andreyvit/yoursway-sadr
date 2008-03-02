
class Foo:
  pass

class Bar:
  def boz(xxxxx):
    print xxxxx ## expr xxxxx => Foo

  def ggg():
    m = Bar.new()
    m.boz(Foo.new())

