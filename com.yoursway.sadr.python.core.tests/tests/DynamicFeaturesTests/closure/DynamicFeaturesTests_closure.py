
var = 0
def f():
    var = 1
    def q():
        return var
    return q
qq = f()
x = qq()    ## value x => 1


