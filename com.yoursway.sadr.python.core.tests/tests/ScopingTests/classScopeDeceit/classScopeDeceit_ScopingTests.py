class C(object):
    a = 1
    def m(self):
        print a ## expr a => str

a = 'string'
C().m()
