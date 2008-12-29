class Aaa:
    def foo(self):
        self.x = 42

def xxx():
    a = Aaa(42)
    a.foo()
    w = a.x
    print w ## value w => 42 

xxx() 
