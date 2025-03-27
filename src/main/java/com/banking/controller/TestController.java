package com.banking.controller;

import com.banking.model.TestEntity;
import com.banking.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    private final TestService testService;
    
    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }
    
    @GetMapping("/connection")
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok(testService.testDatabaseConnection());
    }
    
    @GetMapping
    public ResponseEntity<List<TestEntity>> getAllTests() {
        return ResponseEntity.ok(testService.getAllTests());
    }
    
    @PostMapping
    public ResponseEntity<TestEntity> createTest() {
        TestEntity test = new TestEntity("Test Entry " + System.currentTimeMillis());
        return ResponseEntity.ok(testService.saveTest(test));
    }
}
