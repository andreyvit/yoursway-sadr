/**
 * 
 */
package com.yoursway.sadr.core.constructs;

import java.util.List;

import org.eclipse.core.runtime.Assert;

public class ControlFlowGraph<C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> {
    
    private final List<C> constructs;
    
    public static <C extends IConstruct<C, SC, DC, N>, SC extends StaticContext<C, SC, DC, N>, DC extends DynamicContext, N> ControlFlowGraph<C, SC, DC, N> create(
            List<C> constructs) {
        return new ControlFlowGraph<C, SC, DC, N>(constructs);
    }
    
    public ControlFlowGraph(List<C> constructs) {
        this.constructs = constructs;
    }
    
    public List<C> getNodes() {
        return constructs;
    }
    
    public CfgCursor<C, SC, DC, N> begin() {
        if (constructs.isEmpty())
            return new SpecialCursor(true, true);
        else
            return new Cursor(0);
    }
    
    public CfgCursor<C, SC, DC, N> end() {
        if (constructs.isEmpty())
            return new SpecialCursor(true, true);
        else
            return new SpecialCursor(false, true);
    }
    
    public CfgCursor<C, SC, DC, N> cursorAt(C child) {
        if (constructs.isEmpty())
            return null;
        else {
            int index = constructs.indexOf(child);
            if (index < 0)
                return null;
            else
                return new Cursor(index);
        }
        
    }
    
    class Cursor implements CfgCursor<C, SC, DC, N> {
        
        private final int index;
        
        public Cursor(int index) {
            Assert.isLegal(index >= 0);
            Assert.isLegal(index < constructs.size());
            this.index = index;
        }
        
        public C current() {
            return constructs.get(index);
        }
        
        public CfgCursor<C, SC, DC, N> next() {
            if (index == constructs.size() - 1)
                return new SpecialCursor(false, true);
            else
                return new Cursor(index + 1);
        }
        
        public CfgCursor<C, SC, DC, N> previous() {
            if (index == 0)
                return new SpecialCursor(true, false);
            else
                return new Cursor(index - 1);
        }
        
        public boolean isBof() {
            return false;
        }
        
        public boolean isEof() {
            return false;
        }
        
    }
    
    class SpecialCursor implements CfgCursor<C, SC, DC, N> {
        
        private final boolean bof;
        private final boolean eof;
        
        public SpecialCursor(boolean bof, boolean eof) {
            Assert.isLegal(bof || eof);
            this.bof = bof;
            this.eof = eof;
        }
        
        public C current() {
            throw new UnsupportedOperationException();
        }
        
        public CfgCursor<C, SC, DC, N> next() {
            if (eof)
                throw new UnsupportedOperationException();
            else
                return new Cursor(0);
        }
        
        public CfgCursor<C, SC, DC, N> previous() {
            if (bof)
                throw new UnsupportedOperationException();
            else
                return new Cursor(constructs.size() - 1);
        }
        
        public boolean isBof() {
            return bof;
        }
        
        public boolean isEof() {
            return eof;
        }
        
    }
    
}
