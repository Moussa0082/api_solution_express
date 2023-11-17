package com.solution.express.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solution.express.models.Alerte;
import com.solution.express.services.AlerteService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/alerte")
public class AlerteController {

    @Autowired
    private AlerteService alerteService;

    @GetMapping("/list/{idUtilisateur}")
    @Operation(summary = "affichage des alertes à travers l'id de utilisateur")
    public ResponseEntity<List<Alerte>> listeAlerteByUseur(@PathVariable Integer idUtilisateur){
        return  new ResponseEntity<>(alerteService.getAlerteByUtilisateur(idUtilisateur), HttpStatus.OK);
    }
    
    
}
