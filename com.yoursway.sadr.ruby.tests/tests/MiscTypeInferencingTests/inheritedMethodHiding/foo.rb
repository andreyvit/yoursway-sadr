
class Foo
	
	def foo
		return 10
	end

end

class Bar < Foo

	def foo
		return "test"
	end

end

def boz
	f = Bar.new
	z = f.foo
	puts z ## expr z => String
end
