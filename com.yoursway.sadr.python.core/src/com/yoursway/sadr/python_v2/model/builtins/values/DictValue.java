package com.yoursway.sadr.python_v2.model.builtins.values;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.yoursway.sadr.engine.util.Strings;
import com.yoursway.sadr.python_v2.model.builtins.PythonObject;
import com.yoursway.sadr.python_v2.model.builtins.PythonValue;
import com.yoursway.sadr.python_v2.model.builtins.types.DictType;
import com.yoursway.sadr.python_v2.model.builtins.types.PythonType;

public class DictValue extends PythonValue {
    
    private final HashMap<PythonObject, PythonObject> dict;
    private boolean printing;
    
    public DictValue() {
        this.dict = new HashMap<PythonObject, PythonObject>();
    }
    
    public DictValue(HashMap<PythonObject, PythonObject> dict) {
        this.dict = dict;
    }
    
    public <K, V> String prettify(HashMap<K, V> obj) {
        Iterator<Entry<K, V>> iter = obj.entrySet().iterator();
        String[] items = new String[obj.size()];
        int index = 0;
        while (iter.hasNext()) {
            Entry<K, V> item = iter.next();
            items[index++] = (item.getKey() + ": " + item.getValue());
        }
        Arrays.sort(items, Strings.getNaturalComparator());
        return "{" + Strings.join(items, ", ") + "}";
    }
    
    @Override
    public String describe() {
        if (lockPrinting(true)) {
            String result = prettify(dict);
            lockPrinting(false);
            return result;
        } else
            return "{...}";
    }
    
    public HashMap<PythonObject, PythonObject> getDict() {
        return dict;
    }
    
    public synchronized boolean lockPrinting(boolean newState) {
        if (this.printing == newState)
            return false;
        this.printing = newState;
        return true;
    }
    
    @Override
    public PythonType getType() {
        return DictType.instance;
    }
}
