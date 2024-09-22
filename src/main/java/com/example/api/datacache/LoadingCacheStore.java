package com.example.api.datacache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoadingCacheStore<T> {

    private LoadingCache<String, T> loadingCache;

    public LoadingCacheStore(int maximumSize, int expiryDuration,int refreshAfterWrite, TimeUnit timeUnit, ICacheLoaderService<T> service) {

        loadingCache = CacheBuilder.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterWrite(expiryDuration, timeUnit)
                .refreshAfterWrite(refreshAfterWrite, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build( new CacheLoader<String, T>() {
                    @Override
                    public T load(String key) {
                        System.err.println("public T load(String key)" + key);
                        return service.getBackendData(key);
                    }
                    @Override
                    public Map<String, T> loadAll(Iterable<? extends String> keys) {
                        System.err.println("public Map<String, T> loadAll(Iterable<? extends String> keys) "+ keys);
                        return service.getAllBackendData(keys);
                    }
                    @Override
                    public ListenableFuture<T> reload(final String key, T value) {
                        System.err.println("public ListenableFuture<T> reload(final String key, T value)");
                        if (loadingCache.getIfPresent(key) != null) {
                            return Futures.immediateFuture(value);
                        } else {
                            ListenableFutureTask<T> task = ListenableFutureTask.create(() -> service.getBackendData(key));
                            Executors.newSingleThreadExecutor().execute(task);
                            return task;
                        }
                    }
                });
    }

    public T get(String key)  {
        try {
            return loadingCache.get(key);
        }catch (ExecutionException exception) {
            return null;
        }
    }

    public Map<String, T> getAll(Iterable<? extends String> keys)  {
        try {
            return loadingCache.getAll(keys);
        }catch (ExecutionException exception) {
            return new HashMap<>();
        }
    }

    public void invalidateAll(Iterable<? extends String> keys) {
        loadingCache.invalidateAll(keys);
    }
}
