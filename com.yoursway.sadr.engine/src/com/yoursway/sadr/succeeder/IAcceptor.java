package com.yoursway.sadr.succeeder;

import com.yoursway.sadr.engine.Result;

public interface IAcceptor extends Result {
    <T> void checkpoint(IGrade<T> grade);
}
