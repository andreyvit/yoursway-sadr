package com.yoursway.sadr.python.core.typeinferencing.values;

import java.math.BigInteger;

import com.yoursway.sadr.blocks.foundation.values.AbstractValue;

public class StringValue extends AbstractValue {
    
    private final String value;
    boolean isUnicode = false;
    
    public StringValue(String value) {
        this.value = value;
    }
    
    public StringValue(String value, boolean isUnicode) {
        this.value = value;
        this.isUnicode = isUnicode;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public String describe() {
        String str = "\'" + value + "\'";
        if (isUnicode) {
            StringBuilder result = new StringBuilder();
            for (char el : str.toCharArray()) {
                if (el < 127 && el >= 32)
                    result.append(el);
                else if (el == 9)
                    result.append("\t");
                else if (el == 10)
                    result.append("\n");
                else if (el == 13)
                    result.append("\r");
                else if (el < 16)
                    result.append("\\x0" + Integer.toHexString(el));
                else if (el < 256)
                    result.append("\\x" + Integer.toHexString(el));
                else if (el < 4096)
                    result.append("\\u0" + Integer.toHexString(el));
                else if (el < 65536)
                    result.append("\\u" + Integer.toHexString(el));
            }
            return "u" + result.toString();
        } else
            return str;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isUnicode ? 1231 : 1237);
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        StringValue other = (StringValue) obj;
        if (isUnicode != other.isUnicode)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
    
    public boolean isUnicode() {
        return isUnicode;
    }
    
    public StringValue add(StringValue val) {
        return new StringValue(this.value() + val.value(), this.isUnicode() || val.isUnicode());
    }
    
    public String value() {
        return value;
    }
    
    public StringValue format(StringValue stringValue) {
        return new StringValue(this.value(), this.isUnicode());
    }
    
    public boolean cohersibleToInt() {
        try {
            Integer.valueOf(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
        
    }
    
    public long coerceToInt() {
        return Integer.valueOf(value);
    }
    
    public BigInteger coerceToLong() {
        return new BigInteger(value);
    }
}
