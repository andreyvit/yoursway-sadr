def f():
    def g():
        print a ## expr a => str
    a = 'weird string'
    g()
f()
