package com.yoursway.sadr.python.completion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RubyKeyword {
    
    private final String name;
    
    private static final Map<String, RubyKeyword> keywords = new HashMap<String, RubyKeyword>();
    
    private RubyKeyword(String name) {
        this.name = name;
        keywords.put(name, this);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public static String[] KEYWORDS = { "if", "fi", "elsif", "else", "do", "od", "for", "procedure",
            "return", "local", "global", "end", "and", "or", "not", "exif", "then", "try", "self", "super",
            // pseudo-keywords (standard methods used as keywords)
            "exit", "break", "continue" };
    
    public static String[] findByPrefix(String prefix) {
        List<String> result = new ArrayList<String>();
        for (String element : KEYWORDS)
            if (element.startsWith(prefix))
                result.add(element);
        return result.toArray(new String[result.size()]);
    }
    
}