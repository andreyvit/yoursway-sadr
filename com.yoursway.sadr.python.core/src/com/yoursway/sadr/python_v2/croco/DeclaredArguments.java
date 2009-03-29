package com.yoursway.sadr.python_v2.croco;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python.constructs.ArgumentC;
import com.yoursway.utils.YsCollections;

public class DeclaredArguments {
    
    private final List<ArgumentC> positional;
    private final Map<String, ArgumentC> keyword;
    private final ArgumentC star;
    private final ArgumentC superstar;
    
    public DeclaredArguments(List<ArgumentC> positional, Map<String, ArgumentC> keyword, ArgumentC star,
            ArgumentC superstar) {
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
        for (ArgumentC construct : positional) {
            if (r.length() > 0)
                r.append(", ");
            r.append(construct);
        }
        for (Map.Entry<String, ArgumentC> entry : keyword.entrySet()) {
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
    
    public ArgumentC findPositional(int index) {
        if (index < positional.size())
            return positional.get(index);
        return null;
    }
    
    public ArgumentC findKeyword(String name) {
        return keyword.get(name);
    }
    
}
