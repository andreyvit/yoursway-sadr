
def foo(u)
  if u
    return 10
  else
    return u + foo(u)
  end
end


def bozz()
  z = foo(0)
  puts z	## value z => none
end
