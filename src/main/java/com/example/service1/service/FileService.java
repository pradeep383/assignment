package com.example.service1.service;

import com.example.service1.model.Employee;

import java.util.List;

public interface FileService {
    public void storeData(Employee employee, String fileTYpe) throws Exception;

    List<Employee> readData() throws Exception;

    void updateData(Employee employee, String fileType) throws Exception;
}
