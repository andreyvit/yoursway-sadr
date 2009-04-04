package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kilim.pausable;

import com.google.common.collect.Lists;
import com.yoursway.sadr.engine.Analysis;
import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.goals.ExpressionValueGoal;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.utils.YsCollections;

public class ActualArguments implements Arguments {
    
    private final List<PythonConstruct> positional;
    private final Map<String, PythonConstruct> keyword;
    private final PythonConstruct star;
    private final PythonConstruct superstar;
    private final int hashCode;
    
    public static final Arguments EMPTY = new ActualArguments(Collections.<PythonConstruct> emptyList(),
            Collections.<String, PythonConstruct> emptyMap(), null, null);
    
    public ActualArguments(List<PythonConstruct> positional, Map<String, PythonConstruct> keyword,
            PythonConstruct star, PythonConstruct superstar) {
        if (positional == null)
            throw new NullPointerException("positional is null");
        if (keyword == null)
            throw new NullPointerException("keyword is null");
        this.positional = Lists.immutableList(positional);
        this.keyword = YsCollections.immutableMap(keyword);
        this.star = star;
        this.superstar = superstar;
        this.hashCode = computeHashCode();
    }
    
    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append('(');
        for (PythonConstruct construct : positional) {
            if (r.length() > 0)
                r.append(", ");
            r.append(construct);
        }
        for (Map.Entry<String, PythonConstruct> entry : keyword.entrySet()) {
            if (r.length() > 0)
                r.append(", ");
            r.append(entry.getKey()).append('=').append(entry.getValue());
        }
        if (star != null) {
            if (r.length() > 0)
                r.append(", ");
            r.append("*(").append(star).append(")");
        }
        if (superstar != null) {
            if (r.length() > 0)
                r.append(", ");
            r.append("*(").append(superstar).append(")");
        }
        r.append(')');
        return r.toString();
    }
    
    @pausable
    public PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name,
            PythonConstruct init) {
        dc = dc.unwind();
        PythonConstruct construct = null;
        if (index < positional.size())
            construct = positional.get(index);
        else if (name != null)
            construct = keyword.get(name);
        if (construct != null)
            return Analysis.evaluate(new ExpressionValueGoal(construct, dc));
        // TODO: handle * and ** here (by creating the corresponding array element / dict element goals)
        if (init != null)
            return Analysis.evaluate(new ExpressionValueGoal(init, dc));
        return PythonValueSet.EMPTY;
    }
    
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Set<Alias> aliases, int index, String name, PythonConstruct init) {
        dc = dc.unwind();
        PythonConstruct construct = null;
        if (index < positional.size())
            construct = positional.get(index);
        else if (name != null)
            construct = keyword.get(name);
        if (construct != null)
            PythonAnalHelpers.addRenameForConstruct(punode, aliases, construct, dc);
        if (init != null)
            PythonAnalHelpers.addRenameForConstruct(punode, aliases, init, dc);
    }
    
    public void addTo(ActualArgumentsBuilder builder) {
        builder.addAll(positional, keyword, star, superstar);
    }
    
    @Override
    public int hashCode() {
        return hashCode;
    }
    
    private int computeHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
        result = prime * result + ((positional == null) ? 0 : positional.hashCode());
        result = prime * result + ((star == null) ? 0 : star.hashCode());
        result = prime * result + ((superstar == null) ? 0 : superstar.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ActualArguments other = (ActualArguments) obj;
        if (keyword == null) {
            if (other.keyword != null)
                return false;
        } else if (!keyword.equals(other.keyword))
            return false;
        if (positional == null) {
            if (other.positional != null)
                return false;
        } else if (!positional.equals(other.positional))
            return false;
        if (star == null) {
            if (other.star != null)
                return false;
        } else if (!star.equals(other.star))
            return false;
        if (superstar == null) {
            if (other.superstar != null)
                return false;
        } else if (!superstar.equals(other.superstar))
            return false;
        return true;
    }
    
}
