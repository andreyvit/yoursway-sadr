package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.blocks.foundation.values.RuntimeObject;
import com.yoursway.sadr.python_v2.constructs.PythonVariableAcceptor;
import com.yoursway.sadr.python_v2.croco.Frog;
import com.yoursway.sadr.python_v2.model.builtins.DictType;
import com.yoursway.sadr.python_v2.model.builtins.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.TupleType;
import com.yoursway.sadr.python_v2.model.builtins.TupleValue;

public class ContextImpl {
    
    Map<String, RuntimeObject> args = new HashMap<String, RuntimeObject>();
    
    public ContextImpl(List<PythonArgument> formalArguments, PythonArguments real) {
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
                RuntimeObject value = reader.getKwarg(key, required);
                
                args.put(key, value);
            } else if (argument.getStar() == PythonArgument.STAR) {
                PythonValue<TupleValue> value = TupleType.wrap(reader.lastArgs());
                args.put(key, value);
            } else if (argument.getStar() == PythonArgument.DOUBLESTAR) {
                PythonValue<DictValue> value = DictType.wrapStrDict(reader.lastKwargs());
                args.put(key, value);
            }
        }
    }
    
    public Set<String> keys() {
        return args.keySet();
    }
    
    public void put(String name, RuntimeObject value) {
        args.put(name, value);
    }
    
    public RuntimeObject getActualArgument(String name) {
        return args.get(name);
    }
    
    @Override
    public String toString() {
        return args.toString();
    }
    
    public void findMatchingArguments(Frog name, PythonVariableAcceptor acceptor) {
        for (Entry<String, RuntimeObject> entry : args.entrySet()) {
            if (name.match(entry.getKey())) {
                acceptor.addResult(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public Map<String, RuntimeObject> entries() {
        return args;
    }
}
