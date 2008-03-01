
class Foo

	def test(x)
		puts @xx ## value @xx => 42
	end

end

def ggg
	name = "idontexist"
	m1 = "class Foo; def " + name
	m2 = m1 + "<CR>  @xx = 42<CR>end<CR>end<CR>"
	eval(m2)
end


