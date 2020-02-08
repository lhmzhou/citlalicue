package com.citlalicue.messaging.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.citlalicue.messaging.model.Employee;
import com.citlalicue.messaging.msg.ApiResponse;
import com.citlalicue.messaging.msg.EmployeeUpdateMsg;
import com.citlalicue.messaging.producer.EmployeeProducer;
import com.citlalicue.messaging.service.RepoService;

import com.github.javamockData.Faker;

@RestController
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private Faker mockData = new Faker();

    @Autowired
    EmployeeProducer producer;

    @Autowired
    RepoService repoService;

    private List<Long> lisOfEmployeeIds = new ArrayList<>();

    public boolean loadMockData() {
        for (int i = 0; i < 50; i++) {
            long id = 0;
            EmployeeUpdateMsg employeeInformation = new EmployeeUpdateMsg(id, mockData.address().firstName(),
                mockData.address().lastName(), mockData.job().title(), mockData.job().position(),
                mockData.demographic().gender(), mockData.date().past(1000, TimeUnit.DAYS).toString(),
                mockData.date().birthday().toString(), mockData.random().nextDouble(), mockData.team().name(),
                mockData.address().fullAddress(), mockData.job().keySkills());
            try {
                id = repoService.insertEmployee(emp);
                lisOfEmployeeIds.add(id);
            } catch (ParseException e) {  
                LOGGER.error("Not able to generate employee information, encountering parse exceptions...Here is the error message: {}", e.getMessage());
                return false;
            }

        }
        return true;
    }

    @GetMapping("/api/publish/{numberOfMessages}")
    public ApiResponse<String> publishMockData(@PathVariable int numberOfMessages) {
        LOGGER.info("Publishing employee update messages...");
        if (numberOfMessages > 0) {
            for (int i = 0; i < numberOfMessages; i++) {
                producer.sendEmployeeMessage(
                    generateEmployeeUpdate(lisOfEmployeeIds.get(mockData.random().nextInt(lisOfEmployeeIds.size()))));
            }
        }
        return new ApiResponse<>("Success! Published " + numberOfMessages + " employee updates.");

    }

    @GetMapping("/api/load")
    public ApiResponse<String> loadMockData() {
        LOGGER.info("Loading employee data...");
        if (loadMockData()) {
            return new ApiResponse<>("Employee accounts are initialized");
        }

        return new ApiResponse<>(500, "ERROR: Unable to load data");
    }

    @GetMapping("/api/employee")
    public ApiResponse<List<Employee>> getAllEmployee() {
        LOGGER.info("Getting all Employee data...");
        return new ApiResponse<>(repoService.getAllEmployees());
    }

    @GetMapping("/api/employee/{employeeId}")
    public ApiResponse<Employee> getEmployee(@PathVariable int employeeId) {
        LOGGER.info("Getting data for an Employee...");
        return new ApiResponse<>(repoService.getEmployeeById(employeeId).get());
    }

    @DeleteMapping("/api/employee")
    public ApiResponse<String> deleteAllEmployee() {
        LOGGER.info("Deleting all Employee data...");
        repoService.deleteAllEmployees();
        return new ApiResponse<>("Done. All employees have been deleted.");
    }

    private EmployeeUpdateMsg generateEmployeeUpdate(Long employeeId) {
        return new EmployeeUpdateMsg(employeeId, null, null, mockData.job().title(), mockData.job().position(),
            null, null, null, mockData.random().nextDouble(), mockData.team().name(),
            mockData.address().fullAddress(), mockData.job().keySkills());
    }
}