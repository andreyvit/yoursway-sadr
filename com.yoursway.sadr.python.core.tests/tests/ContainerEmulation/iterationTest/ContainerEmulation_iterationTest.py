
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
q = 0
for i in Container():
    q += 1
print q            ## value q => 0
b = None in Container() ## value b => False



