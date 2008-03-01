

class Foo
end

class Bar

def boz(x)
	putx x ## expr x => Foo
end

end

def gboz(y)
	puts y ## expr y => Foo
end

def ggg()
	m = Bar.new
	m.boz(Foo.new)
	gboz(Foo.new)
end
