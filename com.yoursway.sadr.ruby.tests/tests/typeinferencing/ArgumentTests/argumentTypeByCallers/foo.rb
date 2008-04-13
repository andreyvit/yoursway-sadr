

class Foo
end

class Bar

def myMethod(x)
	putx x ## expr x => Foo
end

end

def myProc(y)
	puts y ## expr y => Foo
end

def ggg()
	m = Bar.new
	m.myMethod(Foo.new)
	myProc(Foo.new)
end

