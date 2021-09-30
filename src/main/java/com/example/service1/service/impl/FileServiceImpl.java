package com.example.service1.service.impl;

import com.example.service1.model.Employee;
import com.example.service1.service.FileService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public void storeData(Employee employee, String fileTYpe) throws Exception {
        final String CSV_LOCATION = "Employees.csv";
        if (fileTYpe.equalsIgnoreCase("CSV")){
            try {
                CSVWriter writer = new CSVWriter(new FileWriter(CSV_LOCATION, true));
                String line[] = {String.valueOf(employee.getId()), employee.getName(), String.valueOf(employee.getAge()), employee.getDob(), String.valueOf(employee.getSalary())};
                writer.writeNext(line);
                writer.flush();
            }
            catch (Exception e) {
                log.error("Error in storing csv data: {}", e);
                throw new Exception("Error in writing data into the csv file");
            }
        }

        if (fileTYpe.equalsIgnoreCase("XML")) {
            try
            {
                JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                File file = new File("employee.xml");
                jaxbMarshaller.marshal(employee, file);
            }
            catch (JAXBException e) {
                log.error("Error in storing xml data: {}", e);
                throw new Exception("Error in writing data into the xml file");
            }
        }

    }

    @Override
    public List<Employee> readData() throws Exception {
        log.info("Inside read Data method...........");

        CSVReader reader = new CSVReader(new FileReader("Employees.csv"), ',');

        List<Employee> emps = new ArrayList<Employee>();

        // read line by line
        String[] record = null;

        while ((record = reader.readNext()) != null) {
            Employee emp = new Employee();
            emp.setId(Integer.valueOf(record[0]));
            emp.setName(record[1]);
            emp.setAge(Integer.valueOf(record[2]));
            emp.setDob(record[3]);
            emp.setSalary(Double.valueOf(record[4]));
            emps.add(emp);
        }

        File xmlFile = new File("employee.xml");

        JAXBContext jaxbContext;
        try
        {
            jaxbContext = JAXBContext.newInstance(Employee.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Employee employee = (Employee) jaxbUnmarshaller.unmarshal(xmlFile);
            emps.add(employee);
        }
        catch (JAXBException e) {
            log.error("XML read error: {}",e);
        }

        reader.close();
        return emps;
    }

    @Override
    public void updateData(Employee employee, String fileType) throws Exception {

        if (fileType.equalsIgnoreCase("CSV")) {
            File inputFile = new File("Employees.csv");

            // Read existing file
            CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
            List<String[]> csvBody = reader.readAll();
            // get CSV row column and replace with by using row and column
            for(int i=0; i<csvBody.size(); i++){
                String[] strArray = csvBody.get(i);

                if(strArray[0].equalsIgnoreCase(String.valueOf(employee.getId()))){ //String to be replaced
                    csvBody.get(i)[1] = employee.getName();
                    csvBody.get(i)[2] = String.valueOf(employee.getAge());
                    csvBody.get(i)[3] = employee.getDob();
                    csvBody.get(i)[4] = String.valueOf(employee.getSalary());

                }
            }
            reader.close();

            // Write to CSV file which is open
            CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',');
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        }

        if (fileType.equalsIgnoreCase("XML")) {
            try
            {
                JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                File file = new File("employee.xml");
                jaxbMarshaller.marshal(employee, file);
            }
            catch (JAXBException e) {
                log.error("Error in storing xml data: {}", e);
                throw new Exception("Error in updating data into the xml file");
            }
        }
    }
}
