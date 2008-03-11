
def foo(u, v)
	if blah
		return bar(u, v)
	else
		return v
	end
end

def bar(x, y)
	if blah
		return foo(y, x)
	else
		return 42
	end 
end

def bozz
  a = 1
  b = 2
  z = bar(a, b)
  puts z	## expr z => Fixnum
  w = bar("a", "b")
  puts w	## expr w => Fixnum,String
end
