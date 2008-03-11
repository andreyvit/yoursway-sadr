

class Foo

def boo
	x = Foo.new
	puts x ## has-method x foo8
end

end

def define_foo(n)
eval "class Foo; def foo" + n + "; end; end"
end


for i in 1..10 
	define_foo(i)
end

