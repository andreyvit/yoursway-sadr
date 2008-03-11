
def f(x)
	if x == 5
		return 1
	else
		return x + f(x+1)
	end
end

def bozz()
   a = 1
   b = 5
   z = f(a)
  puts z	;; expr z => Fixnum
  puts z	;; value z => 11
end
