def f():
    a = 's'
    def g():
        print a ## expr a => str
    g()
    a = 0
f()