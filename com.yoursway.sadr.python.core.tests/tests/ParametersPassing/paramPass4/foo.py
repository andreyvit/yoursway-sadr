
def f(*s_args, **kw_args):
    return s_args[0] + kw_args['py']
r = f(2, py = 1) ## value r => 3

