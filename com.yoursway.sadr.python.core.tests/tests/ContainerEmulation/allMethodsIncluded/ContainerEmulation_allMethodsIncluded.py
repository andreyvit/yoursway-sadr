
class Container(object):
    def __init__(self):
        self.val = 0
    def __len__(self):
        return 1
    def __getitem__(self, key):
        return self.val
    def __setitem__(self, key, value):
        self.val = value
    def __delitem__(self, key):
        self.val = 3
    def __iter__(self):
        class iterator:
            def __iter__(self):
                return self;
            def next(self):
                raise StopIteration
        return iterator()
    def __contains__(self, item):
        return True

c = Container()
l = len(c)         ## value l => 1
item0 = c[None]    ## value item0 => 0
c[None] = 1
item1 = c[None]    ## value item1 => 1
b = None in c      ## value b => True
del c[None]
item2 = c[None]    ## value item2 => 3


