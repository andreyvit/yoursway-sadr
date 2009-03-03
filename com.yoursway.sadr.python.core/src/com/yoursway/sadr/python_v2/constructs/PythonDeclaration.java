package com.yoursway.sadr.python_v2.constructs;

import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;

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
    
    public boolean match(Frog frog);
    
    void index(Krocodile crocodile);
}
