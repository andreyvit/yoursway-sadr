package com.yoursway.sadr.python_v2.goals;

import com.yoursway.sadr.python_v2.constructs.ClassDeclarationC;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.croco.Krocodile;

public class FindClassMethodGoal extends ResolveNameToObjectGoal {
    
    public FindClassMethodGoal(final ClassDeclarationC decl, final Frog matcher, final Krocodile context) {
        super(matcher, decl, context);
    }
}
