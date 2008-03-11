
class Foo

def foo
	return 10
end

def bar
	x = self.foo()
	puts x ## expr x => Fixnum
end

end
