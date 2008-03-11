

class Foo
	def foo
		return 10
	end
	
	def bar
		x = self.foo()
		y = 20
		z = x + y
		puts z ## value z => 30
	end
	
end


def bozz
	x = "abc"
	y = "def"
	z = x + y
	puts z ## value z => "abcdef"
end
