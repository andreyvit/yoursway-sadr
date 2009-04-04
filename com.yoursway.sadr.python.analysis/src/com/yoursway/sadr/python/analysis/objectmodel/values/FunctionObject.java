package com.yoursway.sadr.python.analysis.objectmodel.values;

import java.util.Collection;
import java.util.List;

import kilim.pausable;

import com.yoursway.sadr.python.analysis.Alias;
import com.yoursway.sadr.python.analysis.PythonAnalHelpers;
import com.yoursway.sadr.python.analysis.context.dynamic.PythonDynamicContext;
import com.yoursway.sadr.python.analysis.context.lexical.PythonStaticContext;
import com.yoursway.sadr.python.analysis.index.data.PassedArgumentInfo;
import com.yoursway.sadr.python.analysis.lang.constructs.CallableDeclaration;
import com.yoursway.sadr.python.analysis.lang.constructs.PythonConstruct;
import com.yoursway.sadr.python.analysis.lang.unodes.punodes.Punode;
import com.yoursway.sadr.python.analysis.objectmodel.types.FunctionType;
import com.yoursway.sadr.python.analysis.objectmodel.types.PythonType;
import com.yoursway.sadr.python.analysis.objectmodel.valueset.PythonValueSetBuilder;

public final class FunctionObject extends PythonValue implements CallableObject {
    
    private final String name;
    
    public FunctionObject(CallableDeclaration decl) {
        super(decl);
        this.name = decl.name();
    }
    
    @Override
    public String describe() {
        return "function " + name();
    }
    
    @Override
    public String name() {
        return name;
    }
    
    @Override
    public CallableDeclaration getDecl() {
        return (CallableDeclaration) super.getDecl();
    }
    
    @Override
    @pausable
    public void call(PythonDynamicContext dc, PythonValueSetBuilder builder) {
        PythonAnalHelpers.evaluateConstructs(getDecl().findReturnedValues(), dc, builder);
    }
    
    //    public PythonValueSet call(Krocodile crocodile, RuntimeArguments args) {
    //        List<PythonArgument> nodeArgs = getDecl().getArguments();
    //        ContextImpl context;
    //        try {
    //            context = new ContextImpl(nodeArgs, args);
    //        } catch (PythonException e) {
    //            return new PythonValueSet();
    //        }
    //        Map<String, PythonValueSet> defaults = new HashMap<String, PythonValueSet>();
    //        for (PythonArgument arg : nodeArgs) {
    //            String key = arg.getName();
    //            if (context.getActualArgument(key) == null) {
    //                PythonConstruct init = getDecl().getArgInit(key);
    //                if (init == null)
    //                    return PythonValueSet.EMPTY;
    //                PythonValueSet argDefault = init.evaluate(crocodile);
    //                defaults.put(key, argDefault);
    //            }
    //        }
    //        PythonValueSet results = new PythonValueSet();
    //        for (Map<String, PythonValue> def : new DictIterator<String>(defaults)) {
    //            for (Entry<String, PythonValue> e : def.entrySet()) {
    //                context.put(e.getKey(), e.getValue());
    //            }
    //            Krocodile arguments = new Krocodile(crocodile, getDecl(), context);
    //            PythonValueSet result = getDecl().call(arguments);
    //            results.addResults(result);
    //        }
    //        return results;
    //    }
    
    @Override
    public PythonType getType() {
        return FunctionType.instance;
    }
    
    @Override
    public void bind(PythonValue self, PythonValueSetBuilder builder) {
        builder.addResult(new BoundFunctionObject(this, self));
    }
    
    @Override
    public void computeArgumentAliases(PassedArgumentInfo info, PythonDynamicContext dc, List<Alias> unodes) {
        info.computeAliases(getDecl(), dc, unodes);
    }
    
    @Override
    @pausable
    public void findRenames(Punode punode, PythonStaticContext sc, PythonDynamicContext dc,
            Collection<Alias> aliases) {
        Collection<PythonConstruct> returnedValues = getDecl().findReturnedValues();
        PythonAnalHelpers.addRenamesForConstructs(punode, aliases, returnedValues, dc);
    }
}
