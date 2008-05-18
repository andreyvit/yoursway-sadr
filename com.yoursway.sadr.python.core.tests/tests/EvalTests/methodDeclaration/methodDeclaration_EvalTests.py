
class Foo:
  def test(self, x):
    x = idontexist()
    print x ## expr x => int

def ggg():
  m = """
  def idontexist():
    return 10
  """
  eval(m)


