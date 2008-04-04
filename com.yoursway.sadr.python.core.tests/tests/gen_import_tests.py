from test_gen import TestBuilder
from test_gen import TestModule

#var, class, function
IMPORTED_MODULE_CONTENT = """variable = 0
class Q(object):
    def method(self):
        return 1
def function():
    return 2
"""  
IMPORT_ALL_SCRIPT_CONTENT = """from %s import *

x = variable         ## value x => 0
y = Q().method()     ## value y => 1 
z = function()       ## value z => 2
"""
IMPORT_BY_NAME ="""from %s import variable, Q, function

x = variable         ## value x => 0
y = Q().method()     ## value y => 1 
z = function()       ## value z => 2
"""
IMPORT_BY_ALIAS ="""from %s import variable as v,  Q as C, function as f

x = v                 ## value x => 0
y = C().method()      ## value y => 1 
z = f()               ## value z => 2
"""
SIMPLE_IMPORT = """import %(module)s

x = %(module)s.variable         ## value x => 0
y = %(module)s.Q().method()     ## value y => 1 
z = %(module)s.function()       ## value z => 2
"""

def make_tests(suite_name):
    builder = TestBuilder(suite_name)

    full_module_name = "bar"
    script = IMPORT_ALL_SCRIPT_CONTENT % full_module_name
    modules = [TestModule(full_module_name,IMPORTED_MODULE_CONTENT)]
    builder.addModularTest("importAll",script, modules)
    
    full_module_name = "zig.zag.bar"
    script = IMPORT_ALL_SCRIPT_CONTENT % full_module_name
    modules = [TestModule(full_module_name,IMPORTED_MODULE_CONTENT)]
    builder.addModularTest("packImportAll",script, modules)
    
    full_module_name = "boo.bar"
    modules = [TestModule(full_module_name,IMPORTED_MODULE_CONTENT)]
    
    script = IMPORT_BY_NAME % full_module_name
    builder.addModularTest("importByName",script, modules)
    
    script = IMPORT_BY_ALIAS % full_module_name
    builder.addModularTest("importByAlias",script, modules)

    script = SIMPLE_IMPORT % {"module":full_module_name}
    builder.addModularTest("importModule",script, modules)

if __name__ == "__main__":
    make_tests("ImportTests")
