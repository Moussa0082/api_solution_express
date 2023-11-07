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
import com.solution.express.models.Cotisation;
import com.solution.express.models.Utilisateur;
import com.solution.express.services.CotisationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cotisation")
public class CotisationController {


    @Autowired
    private CotisationService cotisationService;

      //Create user
          @PostMapping("/create")
    @Operation(summary = "Création d'une cotisation")
    public ResponseEntity<Cotisation> createCotisation(
            @Valid @RequestParam("cotisation") String cotisationString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {
        Cotisation cotisation = new Cotisation();
        try {
            cotisation = new JsonMapper().readValue(cotisationString, Cotisation.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Cotisation savedCotisation = cotisationService.createCotisation(cotisation, imageFile);

        return new ResponseEntity<>(savedCotisation, HttpStatus.CREATED);
    }

     
    //Mettre à jour un user
      @PutMapping("/update/{id}")
    @Operation(summary = "Mise à jour d'une cotisation par son Id ")
    public ResponseEntity<Cotisation> updateUtilisateur(
            @PathVariable Integer id,
            @Valid @RequestParam("cotisation") String cotisationString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Cotisation cotisation = new Cotisation();
        try {
            cotisation = new JsonMapper().readValue(cotisationString, Cotisation.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Cotisation cotisationMisAjour = cotisationService.updateCotisation(id, cotisation, imageFile);
            return new ResponseEntity<>(cotisationMisAjour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{idCotisation}/createUser/{idUtilisateur}")
    public Cotisation ajouterUtilisateurCotisation(@PathVariable int idCotisation, @PathVariable int idUtilisateur) throws Exception {
        return cotisationService.ajouterUtilisateurCotisation(idCotisation, idUtilisateur);
    }
     


         @GetMapping("/read")
    //  @Operation(summary = "Affichage de la  liste des utilisateurs")
    public ResponseEntity<List<Cotisation>> getCotisation(){
        return new ResponseEntity<>(cotisationService.getAllCotisation(),HttpStatus.OK);}


       
    //Lire un user spécifique
    @GetMapping("/read/{id}")
    public ResponseEntity<?> getCotisationById(@PathVariable Integer id) {
        return cotisationService.findById(id);
    }


        //Supprimer un utilisateur
           @DeleteMapping("/delete/{id}")
    // @Operation(summary = "Supprimer un utilisateur par son ID")
    public String delete(@Valid @PathVariable Integer id) {
        return cotisationService.deleteCotisation(id);
    }

    
}
