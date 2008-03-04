/**
 * 
 */
package com.yoursway.sadr.python.core.runtime.std;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory.valueSetWith;

import com.yoursway.sadr.python.core.runtime.PythonCallableArgument;
import com.yoursway.sadr.python.core.runtime.PythonBuiltinMethod;
import com.yoursway.sadr.python.core.runtime.PythonMetaClass;
import com.yoursway.sadr.python.core.runtime.PythonRuntimeModel;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory;
import com.yoursway.sadr.python.core.typeinferencing.typesets.internal.SingleTypeSet;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;

public final class NewMethod extends PythonBuiltinMethod {
    
    private final PythonMetaClass klass;
    private final PythonRuntimeModel model;
    
    public NewMethod(PythonMetaClass klass, PythonRuntimeModel model) {
        super(klass, "new", new PythonCallableArgument("arg", PythonCallableArgument.Usage.OPTIONAL));
        this.klass = klass;
        this.model = model;
    }
    
    @Override
    public ValueInfo evaluateBuiltin(ValueInfo receiver, ValueInfo[] arguments) {
        //        TypeSetBuilder builder = new TypeSetBuilder();
        //        for (Type type : receiver.containedTypes())
        //            if (type instanceof MetaClassType)
        //                builder.add(new ClassType(((MetaClassType) type).runtimeMetaClass().instanceClass()));
        //        return builder.build();
        Value value = new InstanceValue(klass.instanceClass(), model.instanceRegistrar());
        SingleTypeSet ts = TypeSetFactory.typeSetWith(new ClassType(klass.instanceClass()));
        return createResult(ts, valueSetWith(value));
    }
}