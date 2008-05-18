from __future__ import with_statement

class CM(object):
    def __init__(self):
        self.attr = 0
    def __enter__(self):
        self.attr = 1
    def __exit__(self, exc_type, exc_value, traceback):
        self.attr = 2
cm = CM()
print cm.attr ## value x => 0
with cm:
    print cm.attr ## value y => 1
print cm.attr ## value z => 2



