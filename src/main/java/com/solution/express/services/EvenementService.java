package com.solution.express.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solution.express.repository.EvenementRepository;

@Service
public class EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    
    
}
