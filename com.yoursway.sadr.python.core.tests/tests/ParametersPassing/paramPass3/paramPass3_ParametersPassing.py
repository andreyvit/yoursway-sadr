
def f(a, b=5, *args):
    return a+b+len(args)
r = f(1,2) ## value r => 3
print r