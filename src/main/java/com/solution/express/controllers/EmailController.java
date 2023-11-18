package com.solution.express.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solution.express.models.Alerte;
import com.solution.express.services.EmailService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "*")
public class EmailController {

    
    @Autowired private EmailService emailService;
 
    @Operation(summary = "Envoyer un mail")
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody Alerte alerte)
    {
        String status
            = emailService.sendSimpleMail(alerte);
 
        return status;
    }
    
}
