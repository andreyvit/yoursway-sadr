package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.types.DictType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;
import com.yoursway.sadr.python_v2.model.builtins.types.TupleType;
import com.yoursway.sadr.python_v2.model.builtins.values.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.values.TupleValue;

public class ContextImpl {
    
    Map<String, PythonObject> args = new HashMap<String, PythonObject>();
    
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
                PythonObject value = reader.getKwarg(key, required);
                
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
    
    public void put(String name, PythonObject value) {
        args.put(name, value);
    }
    
    public PythonObject getActualArgument(String name) {
        return args.get(name);
    }
    
    @Override
    public String toString() {
        return args.toString();
    }
    
    public void findMatchingArguments(Frog name, PythonVariableAcceptor acceptor) {
        for (Entry<String, PythonObject> entry : args.entrySet()) {
            if (name.match(entry.getKey())) {
                acceptor.addResult(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public Map<String, PythonObject> entries() {
        return args;
    }
}
