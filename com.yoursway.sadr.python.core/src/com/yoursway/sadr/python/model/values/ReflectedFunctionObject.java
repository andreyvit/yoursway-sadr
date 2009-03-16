///**
// * 
// */
//package com.yoursway.sadr.python.model.values;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import com.yoursway.sadr.python.objects.RuntimeArguments;
//import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
//import com.yoursway.sadr.python_v2.model.builtins.types.PythonException;
//import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;
//
//public class ReflectedFunctionObject extends BuiltinFunctionObject {
//    private final PythonType classType;
//    private final Method method;
//    
//    public ReflectedFunctionObject(PythonType pythonType, String name, Method method) {
//        super(name);
//        classType = pythonType;
//        this.method = method;
//    }
//    
//    @Override
//    public PythonValue evaluate(RuntimeArguments args) throws PythonException {
//        try {
//            return (PythonValue) method.invoke(classType, args);
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            throw e;
//        } catch (InvocationTargetException e) {
//            Throwable target = e.getTargetException();
//            if (target instanceof PythonException) {
//                throw (PythonException) target;
//            }
//            if (target instanceof RuntimeException)
//                throw (RuntimeException) target;
//            else {
//                throw new IllegalStateException(e.getMessage(), target);
//            }
//        } catch (Exception e) {
//            if (e instanceof PythonException) {
//                throw (PythonException) e;
//            }
//            if (e instanceof RuntimeException) {
//                throw (RuntimeException) e;
//            }
//            if (e.getCause() != null && e.getCause() instanceof RuntimeException)
//                throw (RuntimeException) e.getCause();
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//}