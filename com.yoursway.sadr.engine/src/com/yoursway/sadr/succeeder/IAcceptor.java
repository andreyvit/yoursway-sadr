package com.yoursway.sadr.succeeder;

public interface IAcceptor {
    <T> void checkpoint(IGrade<T> grade);
}
