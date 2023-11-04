package com.solution.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Admin;

public interface AdminRepository  extends JpaRepository<Admin,Integer>{
    

    Boolean existsByEmail(String email);

    Boolean findByEmail(String mail);
}
