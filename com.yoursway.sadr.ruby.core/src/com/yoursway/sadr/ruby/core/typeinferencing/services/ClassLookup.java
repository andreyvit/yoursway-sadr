package com.yoursway.sadr.ruby.core.typeinferencing.services;

import java.util.Set;

import com.yoursway.sadr.ruby.core.runtime.RubyClass;
import com.yoursway.sadr.ruby.core.runtime.std.StandardTypes;

public interface ClassLookup {
    
    RubyClass findClass(String name);
    
    StandardTypes standardTypes();
    
    RubyClass[] allClasses();
    
    RubyClass[] findClassesWithMethod(String method);
    
    Set<RubyClass> findClassesByMethods(String[] methods);
    
}
