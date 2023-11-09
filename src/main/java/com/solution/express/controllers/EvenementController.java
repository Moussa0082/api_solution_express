package com.solution.express.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solution.express.models.Evenement;
import com.solution.express.models.SuperAdmin;
import com.solution.express.services.EvenementService;

@RestController
@RequestMapping("/event")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;


    // @PostMapping("/create")
    // public ResponseEntity<String> createEvenement(@RequestBody Evenement evenement){

    //      String nomEv = evenement.getNomEvenement();
    //      if(evenementService.eventExisteDeja(nomEv)){
    
    //     return new ResponseEntity<>("L' email " + mail + " existe déjà , essayer un autre email.", HttpStatus.UNAUTHORIZED);
          
    //      }
       
    //    return evenementService.createEvenement(evenement);
        
    // }

}
