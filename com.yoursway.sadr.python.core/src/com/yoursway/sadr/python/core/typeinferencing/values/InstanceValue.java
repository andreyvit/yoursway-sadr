package com.yoursway.sadr.python.core.typeinferencing.values;

import java.util.HashMap;
import java.util.Map;

import com.yoursway.sadr.python.core.typeinferencing.services.InstanceRegistrar;
import com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSet;
import com.yoursway.sadr.python_v2.model.builtins.PythonClassType;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;

/**
 * @deprecated due to field value set declaration. Only acceptors are allowed to
 *             work with value sets explicitly.
 */

@Deprecated
//TODO pull up to PythonObject.
public class InstanceValue extends PythonObject {
    
    private final Map<String, ValueSet> fieldValues = new HashMap<String, ValueSet>();
    
    private final int id;
    
    public InstanceValue(PythonClassType receiverType, InstanceRegistrar registrar) {
        super(receiverType);
        id = registrar.registerInstance(this);
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getType().toString()).append("#").append(id).append(" <");
        boolean first = true;
        for (Map.Entry<String, ValueSet> entry : fieldValues.entrySet()) {
            if (first)
                first = false;
            else
                result.append(", ");
            result.append(entry.getKey() + " = " + entry.getValue());
        }
        result.append('>');
        return result.toString();
    }
    
    @Override
    public String describe() {
        return toString();
    }
}
