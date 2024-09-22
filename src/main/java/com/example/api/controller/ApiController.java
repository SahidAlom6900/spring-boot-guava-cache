package com.example.api.controller;

import com.example.api.datacache.LoadingCacheStore;
import com.example.api.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class ApiController {

    @Autowired
    LoadingCacheStore<Employee> employeeLoadingCache;

    @GetMapping("/employee/{id}")
    public Employee searchEmployeeByID(@PathVariable String id) {
        return employeeLoadingCache.get(id);
    }

    @GetMapping("/employees")
    public Map<String,Employee> searchProductNameByID(@RequestBody List<String> ids) {
        return employeeLoadingCache.getAll(ids);
    }

}
