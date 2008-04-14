package com.yoursway.sadr.python.core.typeannotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class AnnotationParser {
    public static final String CALLABLE_KEYWORD = "callable";
    public static final String TYPE_KEYWORD = "type";
    public static final String FROM_KEYWORD = "from";
    public static final String ARROW_KEYWORD = "->";
    private static final Pattern namePattern = Pattern.compile("[A-Za-z]+[A-Za-z,0-9]*|[*]{1,2}|\\^");
    
    /**
     * '^' designate argument of any type. *, ** designate positional and
     * keyword arguments respectively. Alternative types must be separated by
     * '|'.<br/>Function annotation example:
     * <ul>
     * <li><code><b>callable</b> () <b>-></b> ReturnTypec</li>
     * <li><code><b>callable</b> (Type1, Type2|Type3 <b>from</b> module,  Type4) <b>-></b> ReturnType<code></li>
     * <li><code><b>callable</b> (^,*,**) <b>-></b> ReturnType<code></li>
     * </ul>
     */
    private static FunctionAnnotation parseFunctionAnnotation(String source)
            throws AnnotationParsingException {
        if (source.indexOf(CALLABLE_KEYWORD) != 0)
            throw new AnnotationParsingException();
        int argBegin = source.indexOf('(');
        int argEnd = source.indexOf(')');
        if (argEnd - argBegin < 1)
            throw new AnnotationParsingException();
        int arrowEnd = source.indexOf(ARROW_KEYWORD.charAt(ARROW_KEYWORD.length() - 1));
        if (!source.contains(ARROW_KEYWORD) || source.indexOf(ARROW_KEYWORD) <= argEnd)
            throw new AnnotationParsingException();
        
        List<Set<TypeAnnotation>> args = parseArguments(source.substring(argBegin, argEnd));
        if (arrowEnd + 1 >= source.length())
            throw new AnnotationParsingException();
        Set<TypeAnnotation> returnType = parseTypeSet(source.substring(arrowEnd + 1, source.length()));
        return new FunctionAnnotation(args, returnType);
    }
    
    private static List<Set<TypeAnnotation>> parseArguments(String arguementsString)
            throws AnnotationParsingException {
        if (arguementsString.length() <= 2)
            return new ArrayList<Set<TypeAnnotation>>(0);
        String args = arguementsString.substring(1, arguementsString.length());
        String[] argStrs = args.split("[\\s]*,[\\s]*");
        List<Set<TypeAnnotation>> argList = new ArrayList<Set<TypeAnnotation>>(argStrs.length);
        for (int i = 0; i < argStrs.length; i++)
            argList.add(parseTypeSet(argStrs[i]));
        return argList;
    }
    
    private static Set<TypeAnnotation> parseTypeSet(String string) throws AnnotationParsingException {
        Set<TypeAnnotation> set = new HashSet<TypeAnnotation>();
        String[] types = string.split("\\|");
        for (int i = 0; i < types.length; i++)
            set.add(parseType(types[i]));
        return set;
    }
    
    /**
     * Type annotation examples:
     * <ul>
     * <li><code><b>type</b> annotatedName</code></li>
     * <li><code><b>type</b> annotatedName <b>from</b> Module</code></li>
     * </ul>
     */
    private static TypeAnnotation parseTypeAnnotation(String annotationSource)
            throws AnnotationParsingException {
        if (annotationSource.indexOf(TYPE_KEYWORD) != 0)
            throw new AnnotationParsingException();
        return parseType(annotationSource.substring(TYPE_KEYWORD.length()));
    }
    
    private static TypeAnnotation parseType(String string) throws AnnotationParsingException {
        String source = string.trim();
        String typeName;
        String moduleName = null;
        String[] parts = source.split("\\s+");
        if (parts.length < 1)
            throw new AnnotationParsingException();
        typeName = parts[0];
        if (parts.length > 2) {
            if (parts.length != 3)
                throw new AnnotationParsingException();
            if (!parts[1].equals(FROM_KEYWORD))
                throw new AnnotationParsingException();
            moduleName = parts[2];
        }
        //name check
        if (!namePattern.matcher(typeName.subSequence(0, typeName.length())).matches() || moduleName != null
                && !namePattern.matcher(moduleName.subSequence(0, moduleName.length())).matches())
            throw new AnnotationParsingException();
        return new TypeAnnotation(typeName, moduleName);
    }
    
    public static Annotation parseAnnotation(String annotationSource) throws AnnotationParsingException {
        try {
            return parseFunctionAnnotation(annotationSource);
        } catch (AnnotationParsingException e) {
            return parseTypeAnnotation(annotationSource);//else throw exception
        }
    }
}
