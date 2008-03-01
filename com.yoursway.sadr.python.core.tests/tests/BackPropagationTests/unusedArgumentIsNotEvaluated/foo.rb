
def bar(x, y)
  return y 
end

def bozz()
   a = 1
   b = 2
   z = bar(a, b)
  puts a	;; not-cached localvar-type a
  puts b	;; not-cached localvar-type b
  puts z	;; expr z => int
  puts a	;; not-cached localvar-type a
  puts b	;; cached localvar-type b
end
