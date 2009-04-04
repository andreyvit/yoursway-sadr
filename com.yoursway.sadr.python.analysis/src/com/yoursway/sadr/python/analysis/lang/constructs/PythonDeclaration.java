package com.yoursway.sadr.python.analysis.lang.constructs;


/**
 * @author buriy
 * 
 *         Implemented by all constructs that define a name
 * 
 *         "declaration" means variable now has name<br>
 *         (and also type if it would not be python),<br>
 *         "definition" means variable now has both name and a value.<br>
 *         Don't get confused!
 */

public interface PythonDeclaration extends PythonConstruct {
    
    String name();
    
}
