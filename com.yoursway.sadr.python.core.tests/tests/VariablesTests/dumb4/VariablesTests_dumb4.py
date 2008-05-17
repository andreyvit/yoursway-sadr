class Aaa():
    def foo(self):
        self.x = 42

a = Aaa()
a.foo()
w = a.x
print w ## value w => 42 
