package com.solution.express.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.solution.express.models.Banque;
import com.solution.express.models.Utilisateur;
import com.solution.express.services.BanqueService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/banque")
public class BanqueController {


    @Autowired
    private  BanqueService banqueService;

    //Create banque
    @PostMapping("/create")
    @Operation(summary = "Création d'une banque")
    public ResponseEntity<Banque> createBanque(
            @Valid @RequestParam("banque") String banqueString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {

        Banque banque = new Banque();
        try {
            banque = new JsonMapper().readValue(banqueString, Banque.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Banque savedBanque = banqueService.createBanque(banque, imageFile);

        return new ResponseEntity<>(savedBanque, HttpStatus.CREATED);
    }

     
    //Mettre à jour un user
      @PutMapping("/update/{id}")
    @Operation(summary = "Mise à jour d'une banque par son Id ")
    public ResponseEntity<Banque> updateBanque(
            @PathVariable Integer id,
            @Valid @RequestParam("banque") String banqueString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Banque banque = new Banque();
        try {
            banque = new JsonMapper().readValue(banqueString, Banque.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Banque banqueMisAjour = banqueService.updateBanque(id, banque, imageFile);
            return new ResponseEntity<>(banqueMisAjour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     


         @GetMapping("/read")
     @Operation(summary = "Affichage de la  liste des banque")
    public ResponseEntity<List<Banque>> getAllBanque(){
        return banqueService.getAllBanque();}


       
    // Lire un banque spécifique
    @GetMapping("/read/{id}")
    public ResponseEntity<?> getBanqueById(@PathVariable Integer id) {
        return banqueService.findById(id);
    }


        //Supprimer un utilisateur
           @DeleteMapping("/delete/{id}")
    // @Operation(summary = "Supprimer un utilisateur par son ID")
    public String delete(@Valid @PathVariable Integer id) {
        return banqueService.deleteBanque(id);
    }

    
}
