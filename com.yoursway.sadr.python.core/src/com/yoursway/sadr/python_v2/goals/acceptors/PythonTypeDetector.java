package com.yoursway.sadr.python_v2.goals.acceptors;

import java.util.HashMap;

import com.yoursway.sadr.blocks.foundation.types.Type;
import com.yoursway.sadr.blocks.foundation.valueinfo.RuntimeTypeDetector;
import com.yoursway.sadr.blocks.foundation.values.AbstractValue;
import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.blocks.integer_literals.IntegerValue;
import com.yoursway.sadr.blocks.integer_literals.LongValue;
import com.yoursway.sadr.python.core.typeinferencing.values.BooleanValue;
import com.yoursway.sadr.python.core.typeinferencing.values.NilValue;
import com.yoursway.sadr.python.core.typeinferencing.values.StringValue;
import com.yoursway.sadr.python_v2.model.builtins.BooleanType;
import com.yoursway.sadr.python_v2.model.builtins.Builtins;
import com.yoursway.sadr.python_v2.model.builtins.DictType;
import com.yoursway.sadr.python_v2.model.builtins.DictValue;
import com.yoursway.sadr.python_v2.model.builtins.IntegerType;
import com.yoursway.sadr.python_v2.model.builtins.ListType;
import com.yoursway.sadr.python_v2.model.builtins.ListValue;
import com.yoursway.sadr.python_v2.model.builtins.LongType;
import com.yoursway.sadr.python_v2.model.builtins.ObjectType;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.StringType;
import com.yoursway.sadr.python_v2.model.builtins.TupleType;
import com.yoursway.sadr.python_v2.model.builtins.TupleValue;

/**
 * TODO: Consider merging with Builtins
 */
public class PythonTypeDetector extends RuntimeTypeDetector {
    
    private static PythonTypeDetector instance;
    private final HashMap<Class<?>, PythonClassType> types = new HashMap<Class<?>, PythonClassType>();
    
    public PythonTypeDetector() {
        add(IntegerType.instance(), IntegerValue.class);
        add(LongType.instance(), LongValue.class);
        add(StringType.instance(), StringValue.class);
        add(BooleanType.instance(), BooleanValue.class);
        add(ListType.instance(), ListValue.class);
        add(TupleType.instance(), TupleValue.class);
        add(DictType.instance(), DictValue.class);
        add(Builtins.getNone().getType(), NilValue.class);
    }
    
    private void add(PythonClassType type, Class<? extends AbstractValue> value) {
        types.put(value, type);
    }
    
    @Override
    public Type getType(Value value) {
        if (value instanceof PythonValue) {
            //            PythonValue pythonValue = (PythonValue) value;
            //            Class<? extends Object> valueClass = value.getValue().getClass();
            //            if (!types.containsKey(valueClass)) {
            //                throw new IllegalArgumentException("");
            //            }
            //            return types.get(valueClass);
            return ((PythonValue) value).getType();
        } else {
            return ObjectType.instance();
        }
    }
    
    public static RuntimeTypeDetector instance() {
        if (instance == null) {
            instance = new PythonTypeDetector();
        }
        return instance;
    }
    
}
