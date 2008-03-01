
def foo(u, v)
	return v
end

def bar(x, y)
	return foo(y, x) 
end

def bozz()
   a = 1
   b = 2
   z = bar(a, b)
  puts z	## expr z => Fixnum
   w = bar("a", "b")
  puts w	## expr w => String
end
