package com.example.service1.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Data
@XmlRootElement
public class Employee {
    private int id;
    private String name;
    private String dob;
    private double salary;
    private int age;
}
