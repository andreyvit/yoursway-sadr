package com.yoursway.sadr.ruby.core.rewriting.edits;

public class ReplaceEdit extends Edit {
    
    private int offset;
    private final int length;
    private final String text;
    
    public ReplaceEdit(int offset, int length, String text) {
        this.offset = offset;
        this.length = length;
        this.text = text;
    }
    
    @Override
    public int apply(StringBuilder builder) {
        builder.replace(offset, offset + length, text);
        return text.length() - length;
    }
    
    @Override
    int offset() {
        return offset;
    }
    
    @Override
    void fixup(int offset) {
        this.offset += offset;
    }
    
}
