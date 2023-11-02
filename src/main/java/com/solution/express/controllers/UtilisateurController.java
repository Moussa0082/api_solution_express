package com.solution.express.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solution.express.models.Utilisateur;
import com.solution.express.services.UtilisateurService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user")
public class UtilisateurController {

      @Autowired
      private UtilisateurService utilisateurService;

         @GetMapping("/read")
    //  @Operation(summary = "Affichage de la  liste des utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateur(){
        return new ResponseEntity<>(utilisateurService.getAllUtilisateur(),HttpStatus.OK);}
    
}
