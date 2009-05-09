package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.index.IndexRequest;
import com.yoursway.utils.YsCollections;

public class DeclaredArguments {
    
    private final List<Argument> positional;
    private final Map<String, Argument> keyword;
    private final Argument star;
    private final Argument superstar;
    
    public DeclaredArguments(List<Argument> positional, Map<String, Argument> keyword, Argument star,
            Argument superstar) {
        if (positional == null)
            throw new NullPointerException("positional is null");
        if (keyword == null)
            throw new NullPointerException("keyword is null");
        this.positional = Lists.immutableList(positional);
        this.keyword = YsCollections.immutableMap(keyword);
        this.star = star;
        this.superstar = superstar;
    }
    
    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append('(');
        for (Argument construct : positional) {
            if (r.length() > 0)
                r.append(", ");
            r.append(construct);
        }
        for (Map.Entry<String, Argument> entry : keyword.entrySet()) {
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
    
    public Argument findPositional(int index) {
        if (index < positional.size())
            return positional.get(index);
        return null;
    }
    
    public Argument findKeyword(String name) {
        return keyword.get(name);
    }
    
    public void addToIndex(IndexRequest r, PythonLexicalContext inner) {
        for (Argument argument : positional)
            argument.addToIndex(r, inner);
        if (star != null)
            star.addToIndex(r, inner);
    }
    
}
