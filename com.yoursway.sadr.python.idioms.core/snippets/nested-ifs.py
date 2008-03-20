"""
Prefix test.
"""
#source
if expr1:
	print 2
	if expr2:
		print 2
		if expr3:
			if expr4:
				action1
#destination
if expr4:
	print 2
	if expr3:
		print 2
		if expr2:
			if expr1:
				action1
