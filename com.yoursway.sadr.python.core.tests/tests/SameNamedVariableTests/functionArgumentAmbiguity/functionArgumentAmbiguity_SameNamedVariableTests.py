
class dummy(object):
	pass

class idiot(object):
	pass
	
def foo(x):
	x.y = 42
	
def oof(x):
	x.y = "bar"

x = dummy()

def bar():
	foo(x)

def boz(): 
	x = idiot()
	oof(x)
	
m = x.y ## value m => 42
