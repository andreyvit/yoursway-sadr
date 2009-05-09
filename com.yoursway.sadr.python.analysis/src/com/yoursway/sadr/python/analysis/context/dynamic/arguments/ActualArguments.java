package com.yoursway.sadr.python.analysis.context.dynamic.arguments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kilim.pausable;

import com.google.common.collect.Lists;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.aliasing.Alias;
import com.yoursway.sadr.python.analysis.aliasing.AliasConsumer;
import com.yoursway.sadr.python.analysis.context.dynamic.Arguments;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonLexicalContext;
import com.yoursway.sadr.python.analysis.lang.unodes.Bnode;
import com.yoursway.sadr.python.analysis.lang.unodes.Suffix;
import com.yoursway.sadr.python.analysis.lang.unodes.Unode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.DictLiteralUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.literals.ListLiteralUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.DictConcatUnode;
import com.yoursway.sadr.python.analysis.lang.unodes.proxies.ListConcatUnode;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSet;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;
import com.yoursway.utils.YsCollections;

public class ActualArguments implements Arguments {
    
    private final List<Bnode> positional;
    private final Map<String, Bnode> keyword;
    private final Bnode star;
    private final Bnode superstar;
    private final int hashCode;
    
    public static final Arguments EMPTY = new ActualArguments(null, Collections.<Bnode> emptyList(),
            Collections.<String, Bnode> emptyMap(), null, null);
    private final PythonLexicalContext lc;
    
    public ActualArguments(PythonLexicalContext lc, List<Bnode> positional, Map<String, Bnode> keyword,
            Bnode star, Bnode superstar) {
        if (positional == null)
            throw new NullPointerException("positional is null");
        if (keyword == null)
            throw new NullPointerException("keyword is null");
        this.positional = Lists.immutableList(positional);
        this.keyword = YsCollections.immutableMap(keyword);
        this.star = star;
        this.superstar = superstar;
        this.hashCode = computeHashCode();
        this.lc = lc;
    }
    
    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append('(');
        for (Bnode construct : positional) {
            if (r.length() > 0)
                r.append(", ");
            r.append(construct);
        }
        for (Map.Entry<String, Bnode> entry : keyword.entrySet()) {
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
    public PythonValueSet computeArgument(PythonDynamicContext dc, int index, String name, Bnode init,
            Starness starness) {
        dc = dc.unwind();
        final List<Alias> aliases = new ArrayList<Alias>();
        findRenames(Suffix.EMPTY, null, dc, new AliasConsumer() {
            @Override
            public void add(Alias alias) {
                aliases.add(alias);
            }
        }, index, name, init, starness);
        PythonValueSetBuilder builder = PythonValueSet.newBuilder();
        for (Alias alias : aliases)
            builder.addResults(alias.getUnode().calculateValue(lc, dc));
        return builder.build();
    }
    
    @pausable
    public void findRenames(Suffix suffix, PythonLexicalContext sc, PythonDynamicContext dc,
            AliasConsumer aliases, int index, String name, Bnode init, Starness starness) {
        if (lc == null) // only happens for an EMPTY context
            return;
        dc = dc.unwind();
        if (starness == Starness.REGULAR) {
            Bnode construct = null;
            if (index < positional.size())
                construct = positional.get(index);
            else if (name != null)
                construct = keyword.get(name);
            if (construct != null)
                PythonAnalHelpers.addRenameForConstruct(suffix, aliases, construct, dc);
        } else if (starness == Starness.STAR) {
            List<Bnode> items;
            if (index < positional.size())
                items = positional.subList(index, positional.size());
            else
                items = Collections.emptyList();
            Unode list;
            if (star != null)
                if (items.isEmpty())
                    list = star.unode();
                else
                    list = new ListConcatUnode(new ListLiteralUnode(items), star.unode());
            else
                list = new ListLiteralUnode(items);
            PythonAnalHelpers.addRenameForConstruct(suffix, aliases, new Bnode(list, lc), dc);
        } else if (starness == Starness.DOUBLE_STAR) {
            Map<String, Bnode> items = new HashMap<String, Bnode>(keyword);
            // TODO remove all declared named arguments from items
            Unode dict;
            if (superstar == null)
                dict = new DictLiteralUnode(items);
            else if (items.isEmpty())
                dict = superstar.unode();
            else
                dict = new DictConcatUnode(new DictLiteralUnode(items), superstar.unode());
            PythonAnalHelpers.addRenameForConstruct(suffix, aliases, new Bnode(dict, lc), dc);
        }
        if (init != null)
            PythonAnalHelpers.addRenameForConstruct(suffix, aliases, init, dc);
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
