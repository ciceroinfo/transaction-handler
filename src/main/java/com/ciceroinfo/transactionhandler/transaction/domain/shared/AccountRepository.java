package com.ciceroinfo.transactionhandler.transaction.domain.shared;

public interface AccountRepository {
    
    String value(String key);
    
    boolean exists(String key);
    
    boolean notExists(String key);
    
    void add(String key, String value);
    
    void invalidateAll();
}
