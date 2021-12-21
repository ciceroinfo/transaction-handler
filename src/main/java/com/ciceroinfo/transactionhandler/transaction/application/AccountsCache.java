package com.ciceroinfo.transactionhandler.transaction.application;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountsCache implements LocalCache {
    
    private final LoadingCache<String, String> cache;
    
    private AccountsCache() {
        
        CacheLoader<String, String> loader;
        loader = new CacheLoader<>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };
        
        cache = CacheBuilder.newBuilder().build(loader);
    }
    
    @Override
    public String value(String key) {
        return cache.getIfPresent(key);
    }
    
    @Override
    public boolean exists(String key) {
        return value(key) != null;
    }
    
    @Override
    public boolean notExists(String key) {
        return !exists(key);
    }
    
    @Override
    public void add(String key, String value) {
        cache.put(key, value);
    }
    
    @Override
    public void invalidateAll() {
        cache.invalidateAll();
    }
}
