
class Foo


	def test(x)
		putx @xx ## value @xx => 42
	end
	
	def ggg(n)
		m = "@xx = " + n
		eval(m)
	end

end

def boo
	num = "42"
	f = Foo.new
	f.ggg(num)
end
