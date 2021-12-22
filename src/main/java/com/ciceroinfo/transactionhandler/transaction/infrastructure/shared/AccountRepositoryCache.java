package com.ciceroinfo.transactionhandler.transaction.infrastructure.shared;

import com.ciceroinfo.transactionhandler.transaction.domain.shared.AccountRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountRepositoryCache implements AccountRepository {
    
    private final LoadingCache<String, String> cache;
    
    private AccountRepositoryCache() {
        
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
        
        if (key == null) {
            return null;
        }
        
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
