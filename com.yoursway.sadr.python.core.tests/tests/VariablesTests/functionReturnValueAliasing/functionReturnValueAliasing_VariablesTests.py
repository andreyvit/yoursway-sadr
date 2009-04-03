
def foo(p):
    return p

g.x = 42
a = foo(g).x
print a ## value a => 42
