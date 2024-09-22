package com.example.api.services;

import com.example.api.datacache.ICacheLoaderService;
import com.example.api.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService extends ICacheLoaderService<Employee> {

    @Override
    public Employee getBackendData(String id) {
        System.err.println("public Employee getBackendData(String id) ");
        return getEmployeeByID(id);
    }

    @Override
    public Map<String, Employee> getAllBackendData(Iterable<? extends String> ids) {
        System.err.println("public Map<String, Employee> getAllBackendData(Iterable<? extends String> ids)");
        List<String> collect = StreamSupport.stream(ids.spliterator(), true)
                .collect(Collectors.toList());
        System.err.println();
        return getAllEmployees(collect);
    }

    public Employee getEmployeeByID(String id) {
        return new Employee(id,"Employee Name " + id ,"Engineer");
    }

    public Map<String, Employee> getAllEmployees(List<String> ids) {
        HashMap<String, Employee> map = new HashMap<>();
        for ( String id: ids) {
            map.put(id, new Employee(id, "Employee Name " + id, "Engineer"));
        }
        return map;
    }
}
