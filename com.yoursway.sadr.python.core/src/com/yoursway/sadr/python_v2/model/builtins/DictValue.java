package com.yoursway.sadr.python_v2.model.builtins;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.yoursway.sadr.blocks.foundation.values.Value;
import com.yoursway.sadr.engine.util.Strings;
import com.yoursway.sadr.python_v2.model.RuntimeObject;

public class DictValue implements Value {
    
    private final HashMap<RuntimeObject, RuntimeObject> dict;
    private boolean printing;
    
    public DictValue() {
        this.dict = new HashMap<RuntimeObject, RuntimeObject>();
    }
    
    public DictValue(HashMap<RuntimeObject, RuntimeObject> dict) {
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
    
    public String describe() {
        if (lockPrinting(true)) {
            String result = prettify(dict);
            lockPrinting(false);
            return result;
        } else
            return "{...}";
    }
    
    public HashMap<RuntimeObject, RuntimeObject> getDict() {
        return dict;
    }
    
    public synchronized boolean lockPrinting(boolean newState) {
        if (this.printing == newState)
            return false;
        this.printing = newState;
        return true;
    }
}