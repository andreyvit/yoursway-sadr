

class Foo
end

def define_foo(n)
	eval "class Foo; def foo" + i + "<CR>end<CR>end<CR>"
end

define_foo(2)
