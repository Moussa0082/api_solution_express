package com.solution.express.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.solution.express.models.Utilisateur;
import com.solution.express.services.UtilisateurService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UtilisateurController {

      @Autowired
      private UtilisateurService utilisateurService;


      //Create user
          @PostMapping("/create")
    @Operation(summary = "Création d'un utilisateur")
    public ResponseEntity<Utilisateur> createUtilisateur(
            @Valid @RequestParam("utilisateur") String utilisateurString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur = new JsonMapper().readValue(utilisateurString, Utilisateur.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Utilisateur savedUtilisateur = utilisateurService.createUtilisateur(utilisateur, imageFile);

        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }

     
    //Mettre à jour un user
      @PutMapping("/update/{id}")
    @Operation(summary = "Mise à jour d'un utilisateur par son Id ")
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @PathVariable Integer id,
            @Valid @RequestParam("utilisateur") String utilisateurString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur = new JsonMapper().readValue(utilisateurString, Utilisateur.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Utilisateur utilisateurMisAjour = utilisateurService.updateUtilisateur(id, utilisateur, imageFile);
            return new ResponseEntity<>(utilisateurMisAjour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     


         @GetMapping("/read")
    //  @Operation(summary = "Affichage de la  liste des utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateur(){
        return new ResponseEntity<>(utilisateurService.getAllUtilisateur(),HttpStatus.OK);}


       
    //Lire un user spécifique
    @GetMapping("/read/{id}")
    public ResponseEntity<?> getUtilisateurById(@PathVariable Integer id) {
        return utilisateurService.findById(id);
    }


        //Supprimer un utilisateur
           @DeleteMapping("/delete/{id}")
    // @Operation(summary = "Supprimer un utilisateur par son ID")
    public String delete(@Valid @PathVariable Integer id) {
        return utilisateurService.deleteUtilisateur(id);
    }

    
}
