import os, string

try:
    import distutils
    from distutils import sysconfig
    from distutils.command.install import install
    from distutils.core import setup, Extension
except:
    raise SystemExit, "Distutils problem"

tkversion = "__TK-VERSION__"                 ## value tkversion = "__TK-VERSION__"
prefix = "__PREFIX__"                        ## value prefix => "__PREFIX__"
inc_dirs = [prefix + "/include"]             ## value inc_dirs => ["__PREFIX__/include"]
lib_dirs = [prefix + "/lib"]                 ## value lib_dirs => ["__PREFIX__/lib"]
libs = ["tcl" + tkversion, "tk" + tkversion] ## value libs => ["tcl__TK-VERSION__", "tk__TK-VERSION__"]

name = "Tkinter"                             ## value name => "Tkinter"
description = "Tk Extension to Python"       ## value description => "Tk Extension to Python"
ext_modules_args = (
	'_tkinter', 
	['_tkinter.c', 'tkappinit.c']            ## value ext_modules_args => ("_tkinter", ["_tkinter.c", "tkappinit.c"])
ext_modules_kwargs = {                       
    define_macros=[('WITH_APPINIT', 1)],
    include_dirs = inc_dirs,
    libraries = libs,
    library_dirs = lib_dirs)]
}
