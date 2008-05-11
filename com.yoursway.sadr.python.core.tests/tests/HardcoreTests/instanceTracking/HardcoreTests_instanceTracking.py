
class Bar:
  pass
  
class Xxx:
  pass
  
class Moo:
  def __init__(self, p):
    self.bbb = p.q
    
  def boo(self):
    return self.ccc
    
mmm = Moo("s")
mmm.ccc = "bbbbbb"

s = Bar()
xxx = Xxx()
s.boz = xxx
moo = Moo(s)
moo.ccc = moo.bbb
bar = moo.boo()
xxx.biz = 42
foo = bar.boz.biz
print foo ## expr foo => int

