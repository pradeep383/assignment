package com.example.service1.controller;

import com.example.service1.model.Employee;
import com.example.service1.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/store")
    public String storeData(@RequestBody Employee employee, @RequestParam String fileType) throws Exception{
        fileService.storeData(employee, fileType);
        return "Data Stored Successfully";
    }

    @GetMapping("/read")
    public List<Employee> readData() throws Exception{
        return fileService.readData();
    }

    @PutMapping("/update")
    public String updateData(@RequestBody Employee employee, @RequestParam String fileType) throws Exception{
        fileService.updateData(employee, fileType);
        return "Data Stored Successfully";
    }
}
