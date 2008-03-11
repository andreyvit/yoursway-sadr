
class Bar
  def foo(x)
    puts "Hello, world!"
  end
end

class Boz
  def foo(x)
    puts "Hi, world!"
  end
  def func()
    x = Bar.new
    self.foo(42)
    x.foo(24)
  end
end
