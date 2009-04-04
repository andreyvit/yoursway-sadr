package com.yoursway.sadr.python.analysis.context.lexical;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.StaticContext;
import com.yoursway.sadr.core.propagation.PropagationTracker;
import com.yoursway.sadr.core.propagation.PropagationTrackerImpl;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.areas.Area;
import com.yoursway.sadr.python.analysis.context.lexical.scopes.PythonScope;
import com.yoursway.sadr.python.analysis.unused.PythonConstruct;

public final class PythonLexicalContext implements
        StaticContext<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode> {
    
    private final PythonScope scope;
    private final Area area;
    
    public PythonLexicalContext(PythonScope scope, Area area) {
        if (scope == null)
            throw new NullPointerException("scope is null");
        if (area == null)
            throw new NullPointerException("area is null");
        this.scope = scope;
        this.area = area;
    }
    
    public PythonScope getScope() {
        return scope;
    }
    
    public Area getArea() {
        return area;
    }
    
    public PropagationTracker<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode> propagationTracker() {
        return new PropagationTrackerImpl<PythonConstruct, PythonLexicalContext, PythonDynamicContext, ASTNode>();
    }
    
    public PythonLexicalContext with(PythonScope scope) {
        return new PythonLexicalContext(scope, area);
    }
    
    public PythonLexicalContext with(Area area) {
        return new PythonLexicalContext(scope, area);
    }
    
    @Override
    public String toString() {
        return scope.toString();
    }
    
    @Override
    public int hashCode() {
        return (scope.hashCode() + 31) * 31 + area.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        PythonLexicalContext that = (PythonLexicalContext) obj;
        return this.area.equals(that.area) && this.scope.equals(that.scope);
    }
    
}
