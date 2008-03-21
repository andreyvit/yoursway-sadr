package com.yoursway.sadr.ruby.core.staticchecks;

import com.yoursway.sadr.engine.Result;

public enum Nullability implements Result {
    CanBeNull, CannotBeNull, Unknown
}
