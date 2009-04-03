
def foo(p):
    return p.x

g.x = 42
a = foo(g)
print a ## value a => 42
