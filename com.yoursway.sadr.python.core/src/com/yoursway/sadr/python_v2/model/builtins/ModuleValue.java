package com.yoursway.sadr.python_v2.model.builtins;

import com.yoursway.sadr.blocks.foundation.values.Value;

public class ModuleValue implements Value {
    
    private final String var;
    private final String path;
    private final String alias;
    
    public ModuleValue(String path, String alias, String var) {
        this.path = path;
        this.var = var;
        if (var != null && var.length() == 0) {
            throw new IllegalArgumentException("Empty variable name, use null instead");
        }
        this.alias = alias;
        if (alias != null && alias.length() == 0) {
            throw new IllegalArgumentException("Empty alias name, use null instead");
        }
    }
    
    @Override
    public String toString() {
        return describe();
    }
    
    public String describe() {
        String out = "";
        if (var == null)
            out = "import " + path;
        else
            out = "from " + path + " import " + var;
        if (!alias.equals(path))
            out += " as " + alias;
        return out;
    }
    
    public String getPath() {
        return path;
    }
    
    public String getVar() {
        return var;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((var == null) ? 0 : var.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ModuleValue other = (ModuleValue) obj;
        if (alias == null) {
            if (other.alias != null)
                return false;
        } else if (!alias.equals(other.alias))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (var == null) {
            if (other.var != null)
                return false;
        } else if (!var.equals(other.var))
            return false;
        return true;
    }
}
