package com.yoursway.sadr.python.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python.model.types.DictType;
import com.yoursway.sadr.python.model.types.PythonException;
import com.yoursway.sadr.python.model.types.TupleType;
import com.yoursway.sadr.python.model.values.DictValue;
import com.yoursway.sadr.python.model.values.TupleValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;

public class ContextImpl {
    
    Map<String, PythonValue> args = new HashMap<String, PythonValue>();
    
    public ContextImpl(List<PythonArgument> formalArguments, RuntimeArguments real) throws PythonException {
        PythonArgumentsReader reader = new PythonArgumentsReader(real);
        for (ASTNode node : formalArguments) {
            //            if (!(node instanceof PythonArgument))
            //                throw new IllegalStateException("Wrong argument: Expected PythonArgument, got "
            //                        + node.getClass().getSimpleName());
            PythonArgument argument = (PythonArgument) node;
            ASTNode initialization = argument.getInitialization();
            String key = argument.getName();
            if (argument.getStar() == PythonArgument.NOSTAR) {
                boolean required = initialization == null;
                PythonValue value = reader.getKwarg(key, required);
                
                args.put(key, value);
            } else if (argument.getStar() == PythonArgument.STAR) {
                TupleValue value = TupleType.wrap(reader.lastArgs());
                args.put(key, value);
            } else if (argument.getStar() == PythonArgument.DOUBLESTAR) {
                DictValue value = DictType.wrapStrDict(reader.lastKwargs());
                args.put(key, value);
            }
        }
    }
    
    public Set<String> keys() {
        return args.keySet();
    }
    
    public void put(String name, PythonValue value) {
        args.put(name, value);
    }
    
    public PythonValue getActualArgument(String name) {
        return args.get(name);
    }
    
    @Override
    public String toString() {
        return args.toString();
    }
    
    public Map<String, PythonValue> entries() {
        return args;
    }
}
