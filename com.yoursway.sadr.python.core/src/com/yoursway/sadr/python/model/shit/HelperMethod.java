package com.yoursway.sadr.python.model.shit;
//package com.yoursway.sadr.python_v2.goals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.yoursway.sadr.python.model.types.InstanceType;
//import com.yoursway.sadr.python.model.types.PythonType;
//import com.yoursway.sadr.python.model.values.InstanceValue;
//import com.yoursway.sadr.python_v2.constructs.PythonConstruct;
//import com.yoursway.sadr.python_v2.goals.acceptors.PythonValueSet;
//import com.yoursway.sadr.python_v2.goals.acceptors.TupleIterator;
//import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
//
//public class HelperMethod {
//    
//    @Override
//    public PythonValueSet evaluate() {
//        acceptor = new PythonValueSet();
//        
//        List<PythonConstruct> superClasses = classDeclarationC.getSuperClasses();
//        ArrayList<PythonValueSet> supers = new ArrayList<PythonValueSet>();
//        for (PythonConstruct superClass : superClasses) {
//            PythonValueSet sup = superClass.evaluate(getKrocodile());
//            if (!sup.isEmpty())
//                supers.add(sup);
//        }
//        for (List<PythonValue> results : new TupleIterator(supers)) {
//            ArrayList<PythonType> types = convertToTypes(results);
//            if (types != null) {
//                InstanceType receiverType = new InstanceType(classDeclarationC);
//                receiverType.setSuperClasses(types);
//                PythonValue receiver = new InstanceValue(receiverType);
//                acceptor.addResult(receiver, getKrocodile());
//            }
//        }
//        return acceptor;
//    }
//    
//}
