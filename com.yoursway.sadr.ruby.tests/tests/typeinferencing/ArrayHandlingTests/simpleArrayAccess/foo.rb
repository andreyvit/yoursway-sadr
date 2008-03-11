

class Foo
end

def foo
	x = []
	x[1] = Foo.new
	return x
end


def bar
	zzz = foo ## expr zzz => ?[],Foo[]
	y = zzz[1]
	puts y ## expr y => Foo
end
