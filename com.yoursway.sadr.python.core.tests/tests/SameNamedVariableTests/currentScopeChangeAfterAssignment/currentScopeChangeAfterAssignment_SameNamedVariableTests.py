
class dummy(object):
	pass

class idiot(object):
	pass

c = dummy()
b = c
c.x = 42

def foo():
	c = idiot()
	c.x = 10
	d = b
	y = d.x
	print y ## value y => 42
