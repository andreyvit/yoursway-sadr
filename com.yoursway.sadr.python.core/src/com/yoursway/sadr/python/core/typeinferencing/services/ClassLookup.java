package com.yoursway.sadr.python.core.typeinferencing.services;

import java.util.Set;

import com.yoursway.sadr.python.core.runtime.RubyClass;
import com.yoursway.sadr.python.core.runtime.std.StandardTypes;

public interface ClassLookup {
    
    RubyClass findClass(String name);
    
    StandardTypes standardTypes();
    
    RubyClass[] allClasses();
    
    RubyClass[] findClassesWithMethod(String method);
    
    Set<RubyClass> findClassesByMethods(String[] methods);
    
}
