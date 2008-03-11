
class Foo
end


def foo
for i in 0..10
end
bar(i)
end

def bar(m)
x = m
puts x ## value x => 10
end
