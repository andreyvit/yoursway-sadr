

class Foo

def test(x)
	x = self.idontexist()
	puts x ## expr x => Fixnum
end

end

def ggg
	name = "idontexist"
	m1 = "class Foo; def " + name
	m2 = m1 + "<CR> return 10<CR>end<CR>end<CR>"
	eval(m2)
end

