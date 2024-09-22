package com.example.api.datacache;

import java.util.Map;

public abstract class ICacheLoaderService<T> {

    public abstract T getBackendData(String id);
    public Map<String, T> getAllBackendData(Iterable<? extends String> keys){
        return null;
    }
}