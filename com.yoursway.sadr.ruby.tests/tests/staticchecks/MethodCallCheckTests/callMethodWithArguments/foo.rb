
class Bar

	def foo(x, y)
	end

end

def ggg
	x = Bar.new
	
	x.foo(5, 3)
	x.foo(5)
	x.foo(5, 3, 1)
end
