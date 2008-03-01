

class Foo
end

def bar
	x = Foo.new
	y = x
	puts y ## expr y => Foo
end
