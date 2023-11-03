package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer> {
    
     Boolean existsByEmail(String email);

}
