
class Foo
end

def foo
	for i in 1..3
		for j in 1..3
			z = i + j
		end
	end
	z = 42
	puts z ## value z => 2,3,4,5,6,42
end

