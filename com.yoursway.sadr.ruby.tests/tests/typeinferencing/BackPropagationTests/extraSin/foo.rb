
def bar(x, y)
	x = x
	y = y + x
  return 14
end

def bozz()
   a = 1
   b = 2
   z = bar(a, b)
  puts z	## expr z => Fixnum
  w = bar("a", "b")
  puts w	## expr w => Fixnum
end
