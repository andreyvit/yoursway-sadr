
class Foo
end

def foo
	for i in 1..10
		bar(i)
	end
end

def bar(m)
	puts m ## value m => 1,2,3,4,5,6,7,8,9,10
end
