package com.example.api.datacache;

import com.example.api.entities.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {

    @Bean
    public LoadingCacheStore<Employee> employeeLoadingCache(ICacheLoaderService<Employee> employeeService) {
        return new LoadingCacheStore<>(1000,600,600, TimeUnit.SECONDS, employeeService);
    }

    @Bean
    public LoadingCacheStore<String> productNameLoadingCache(ICacheLoaderService<String> productService) {
        return new LoadingCacheStore<>(1000,300,300, TimeUnit.SECONDS, productService);
    }
}
