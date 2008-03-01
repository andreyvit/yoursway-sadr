
def userx(x,y)
	return x
end

def usery(x,y)
	return y
end

def bar(x, y, z)
  return userx(x,y) + usery(x,y)
end

def bozz()
   a = 1
  b = 2
  z = bar(a, b, a)
  puts z	## expr z => Fixnum
   w = bar("a", "b", b)
  puts w	## expr w => String
end


def boz2()
	z = bar("a", "b", 0)
 	puts z	## expr z => String
end

