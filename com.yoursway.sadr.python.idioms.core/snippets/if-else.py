"""
Invert if-else block
"""
#source
if expr1:
	action1
else:
	action2
#destination
if not expr:
	action2
else:
	action1
#source
if not expr:
	action1
else:
	action2
#destination
if expr:
	action2
else:
	action1
