package com.banking.repository;

import com.banking.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    
    // Simple query to test connection
    @Query("SELECT COUNT(t) FROM TestEntity t")
    Long countEntities();
}
