

class Foo

def foo
end

end

class Bar

def boz(x)
	x.foo
	puts x 	## expr x => Foo
end

end

def gboz(x)
	x.foo
	puts x ## expr x => Foo
end
