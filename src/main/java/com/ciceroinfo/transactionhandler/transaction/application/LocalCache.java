package com.ciceroinfo.transactionhandler.transaction.application;

public interface LocalCache {
    
    String value(String key);
    
    boolean exists(String key);
    
    boolean notExists(String key);
    
    void add(String key, String value);
    
    void invalidateAll();
}
