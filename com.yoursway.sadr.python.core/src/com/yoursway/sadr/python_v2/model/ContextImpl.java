package com.yoursway.sadr.python_v2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.python.parser.ast.PythonArgument;

import com.yoursway.sadr.python_v2.model.builtins.DictType;
import com.yoursway.sadr.python_v2.model.builtins.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.PythonObjectWithValue;
import com.yoursway.sadr.python_v2.model.builtins.TupleType;
import com.yoursway.sadr.python_v2.model.builtins.TupleValue;

public class ContextImpl implements Context {
    
    Map<String, RuntimeObject> args = new HashMap<String, RuntimeObject>();
    
    public ContextImpl(List<ASTNode> formalArguments, PythonArguments real) {
        PythonArgumentsReader reader = new PythonArgumentsReader(real);
        for (ASTNode node : formalArguments) {
            if (!(node instanceof PythonArgument))
                throw new IllegalStateException("Wrong argument: Expected PythonArgument, got "
                        + node.getClass().getSimpleName());
            PythonArgument argument = (PythonArgument) node;
            ASTNode initialization = argument.getInitialization();
            String key = argument.getName();
            if (argument.getStar() == PythonArgument.NOSTAR) {
                boolean required = initialization == null;
                RuntimeObject value = reader.getKwarg(key, required);
                args.put(key, value);
            } else if (argument.getStar() == PythonArgument.STAR) {
                PythonObjectWithValue<TupleValue> value = TupleType.wrap(reader.lastArgs());
                args.put(key, value);
            } else if (argument.getStar() == PythonArgument.DOUBLESTAR) {
                PythonObjectWithValue<DictValue> value = DictType.wrapStrDict(reader.lastKwargs());
                args.put(key, value);
            }
        }
    }
    
    public boolean contains(String name) {
        return args.containsKey(name);
    }
    
    public RuntimeObject getActualArgument(String name) {
        return args.get(name);
    }
    
}
