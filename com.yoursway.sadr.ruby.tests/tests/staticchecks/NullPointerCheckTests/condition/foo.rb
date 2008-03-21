
def foo(a)
	a.class
	if a
		a + 3
	end
end

def ggg
	foo(4)
	foo(nil)
end
