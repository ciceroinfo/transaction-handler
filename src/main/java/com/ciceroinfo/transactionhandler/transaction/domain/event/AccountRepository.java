package com.ciceroinfo.transactionhandler.transaction.domain.event;

public interface AccountRepository {
    
    Integer value(String key);
    
    boolean exists(String key);
    
    boolean notExists(String key);
    
    void add(String key, Integer value);
    
    void invalidateAll();
}
