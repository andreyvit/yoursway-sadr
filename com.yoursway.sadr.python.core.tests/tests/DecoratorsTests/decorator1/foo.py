
def decorator(fn):
    def decoration(arg):
        return fn(arg) + 1
    return decoration
@decorator
@decorator
def func(arg):
    return arg

x = func(0) ## value x => 2 

