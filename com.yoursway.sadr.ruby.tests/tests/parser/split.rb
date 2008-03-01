def magic(filename, prefix, javaPrefix)
  f = File.read(filename)

  num = 1
  out = File.new("#{prefix}#{num}.dtl", "w")

  f.each_line { |l|
    if l =~ /^\s*$/
      out.close
      num += 1
      out = File.new("#{prefix}#{num}.dtl", "w")
    else
      out << l
    end
  }

  out.close

  num -= 1

  for i in 1..num 
    puts "@Test\npublic void test#{javaPrefix}#{i}() throws Exception {\nString content = loadContent(\"/tests/#{prefix}#{i}.dtl\");\nModuleDeclaration moduleDeclaration = parse(content);\nSystem.out.println(moduleDeclaration);\n}\n"
  end
end

magic("constructs.dtl", "", "")
magic("oo-constructs.dtl", "oo", "OO_")