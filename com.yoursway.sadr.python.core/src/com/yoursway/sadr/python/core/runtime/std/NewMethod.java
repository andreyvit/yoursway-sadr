/**
 * 
 */
package com.yoursway.sadr.python.core.runtime.std;

import static com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo.createResult;
import static com.yoursway.sadr.python.core.typeinferencing.valuesets.ValueSetFactory.valueSetWith;

import com.yoursway.sadr.python.core.runtime.RubyArgument;
import com.yoursway.sadr.python.core.runtime.RubyBuiltinMethod;
import com.yoursway.sadr.python.core.runtime.RubyMetaClass;
import com.yoursway.sadr.python.core.runtime.RubyRuntimeModel;
import com.yoursway.sadr.python.core.typeinferencing.goals.ValueInfo;
import com.yoursway.sadr.python.core.typeinferencing.types.ClassType;
import com.yoursway.sadr.python.core.typeinferencing.typesets.TypeSetFactory;
import com.yoursway.sadr.python.core.typeinferencing.typesets.internal.SingleTypeSet;
import com.yoursway.sadr.python.core.typeinferencing.values.InstanceValue;
import com.yoursway.sadr.python.core.typeinferencing.values.Value;

public final class NewMethod extends RubyBuiltinMethod {
    
    private final RubyMetaClass klass;
    private final RubyRuntimeModel model;
    
    public NewMethod(RubyMetaClass klass, RubyRuntimeModel model) {
        super(klass, "new", new RubyArgument("arg", RubyArgument.Usage.OPTIONAL));
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