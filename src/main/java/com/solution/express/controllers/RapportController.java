package com.solution.express.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.solution.express.services.RapportService;

@RestController
public class RapportController {

    @Autowired
    private RapportService rapportService;
    
}
