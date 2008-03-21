package com.yoursway.sadr.ruby.core.typeinferencing.constructs;

import java.util.Collection;

import org.eclipse.dltk.ast.ASTNode;

import com.yoursway.sadr.core.constructs.IConstruct;
import com.yoursway.sadr.ruby.core.typeinferencing.goals.ThingAccessInfo;

public interface RubyConstruct extends
        IConstruct<RubyConstruct, RubyStaticContext, RubyDynamicContext, ASTNode> {
    
    Collection<ThingAccessInfo> accessInfos();
}