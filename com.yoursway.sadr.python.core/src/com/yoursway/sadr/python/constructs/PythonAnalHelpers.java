package com.yoursway.sadr.python.constructs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import kilim.pausable;

import com.yoursway.sadr.python.index.AssignmentsIndexQuery;
import com.yoursway.sadr.python.index.AssignmentsRequestor;
import com.yoursway.sadr.python.index.unodes.Unode;

public class PythonAnalHelpers {

    public static Collection<PythonConstruct> chooseAssignmentsFromInnermostScope(
            final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope,
            final List<PythonScope> scopes) {
        for (PythonScope scope : scopes) {
            Collection<PythonConstruct> assignedValues = assignmentsByScope.get(scope);
            if (!assignedValues.isEmpty())
                return assignedValues;
        }
        return Collections.emptyList();
    }

    @pausable
    public static void findAssignmentsAndGroupByScopes(PythonStaticContext staticContext, Unode unode,
            final Map<PythonScope, Collection<PythonConstruct>> assignmentsByScope,
            final List<PythonScope> scopes) {
        PythonScope outerScope = scopes.get(scopes.size() - 1);
        final Collection<PythonConstruct> outerScopeConstructs = assignmentsByScope.get(outerScope);
        
        staticContext.getIndex().query(new AssignmentsIndexQuery(unode), new AssignmentsRequestor() {
            public void assignment(PythonConstruct rhs, PythonFileC fileC) {
                Collection<PythonConstruct> constructs = assignmentsByScope.get(rhs.staticContext());
                if (constructs == null)
                    constructs = outerScopeConstructs;
                constructs.add(rhs);
                
            }
        });
    }
    
}
