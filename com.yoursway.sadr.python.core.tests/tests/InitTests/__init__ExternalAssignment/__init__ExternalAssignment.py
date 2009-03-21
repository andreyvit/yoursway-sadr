class Aaa():
	pass
	
def initAaa(self):
    self.x = 42
    
Aaa.__init__ = initAaa

a = Aaa()
w = a.x
print w ## value w => 42
