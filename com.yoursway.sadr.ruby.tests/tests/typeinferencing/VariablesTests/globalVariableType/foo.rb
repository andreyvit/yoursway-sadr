

class Foo

def foo
	$qwerty = 10
end

end

def bozz
	puts $qwerty ## expr $qwerty => Fixnum
end

