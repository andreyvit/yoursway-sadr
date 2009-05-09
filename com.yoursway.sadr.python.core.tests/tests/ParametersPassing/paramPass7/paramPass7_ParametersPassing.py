
def f(p_arg, *s_args, **kw_args):
    return (s_args[0] + kw_args['py'])+p_arg
r = f(3, 2, py = 1) ## value r => 6
