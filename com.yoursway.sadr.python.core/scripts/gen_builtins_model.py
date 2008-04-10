PATH = "../"
SUFFIX = '.properties'

CALLABLE_POSTFIX = " %(args)s -> %(ret_val)s"

def make_annotation(namespace):
    CALLABLES = frozenset(['function', 
                           'instancemethod', 
                           'builtin_function_or_method', 
                           'wrapper_descriptor',
                           'method_descriptor']);
    annotation = type(namespace).__name__
    if annotation in CALLABLES:
        annotation = "callable" + CALLABLE_POSTFIX % {'args':'?', 'ret_val':'?'}
    return annotation

def is_traversible(namespace):
    name = type(namespace).__name__
    return name == 'type' or name == 'module'

def process_names(file, prefix, namespace):
    for name, value in vars(namespace).items():
        full_name = prefix + name
        print >> file, full_name + ' = ' + make_annotation(value)
        if (is_traversible(value)):
            process_names(file, full_name + '.', value)
        

def make_properties(module):
    file = open(PATH + module.__name__ + SUFFIX,'w')
    
    process_names(file, '', module)

if __name__ == '__main__':
    make_properties(__builtins__)
    print "generation complete"
