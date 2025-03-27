package com.banking.service;

import com.banking.model.TestEntity;
import com.banking.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    
    private final TestRepository testRepository;
    
    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }
    
    public TestEntity saveTest(TestEntity test) {
        return testRepository.save(test);
    }
    
    public List<TestEntity> getAllTests() {
        return testRepository.findAll();
    }
    
    public Long countTests() {
        return testRepository.countEntities();
    }
    
    public String testDatabaseConnection() {
        try {
            Long count = countTests();
            return "Database connection successful! Found " + count + " test entities.";
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}
