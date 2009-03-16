//package com.yoursway.sadr.python.model.values;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.eclipse.dltk.python.parser.ast.PythonArgument;
//
//import com.yoursway.sadr.python.objects.ContextImpl;
//import com.yoursway.sadr.python.objects.RuntimeArguments;
//import com.yoursway.sadr.python_v2.constructs.CallableDeclaration;
//import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
//import com.yoursway.sadr.python_v2.croco.Krocodile;
//import com.yoursway.sadr.python_v2.goals.acceptors.DictIterator;
//import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
//import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
//import com.yoursway.sadr.python_v2.model.builtins.types.FunctionType;
//import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;
//import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;
//
//public class FunctionObject extends PythonValue implements CallableObject {
//    private final String name;
//    
//    //    private final PythonValue object;
//    
//    public FunctionObject(CallableDeclaration decl, PythonValue object) {
//        super(decl);
//        //        this.object = object; // 
//        this.name = decl.name();
//    }
//    
//    public FunctionObject(String name, PythonValue object) { // unbound
//        super(null);
//        //        this.object = object;
//        this.name = name;
//    }
//    
//    @Override
//    public String describe() {
//        return "function " + name();
//    }
//    
//    @Override
//    public String name() {
//        return name;
//    }
//    
//    @Override
//    public CallableDeclaration getDecl() {
//        return (CallableDeclaration) super.getDecl();
//    }
//    
//    protected Krocodile addContext(Krocodile crocodile, RuntimeArguments args, List<PythonArgument> formalArgs)
//            throws PythonException {
//        ContextImpl context = new ContextImpl(formalArgs, args);
//        Krocodile actualArguments = new Krocodile(crocodile, getDecl(), context);
//        return actualArguments;
//    }
//    
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
//                    //XXX:can't find argument initialization. This is a true error.
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
//    
//    @Override
//    public PythonType getType() {
//        return FunctionType.instance;
//    }
//}
