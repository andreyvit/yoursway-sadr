

class Foo

def foo
	return 10
end

def bozz
	$qwerty = self.foo	;; expr $qwerty => Fixnum
end

end
