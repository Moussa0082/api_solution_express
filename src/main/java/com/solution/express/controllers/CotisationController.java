package com.solution.express.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.solution.express.models.Cotisation;
import com.solution.express.models.SuperAdmin;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.CotisationRepository;
import com.solution.express.repository.UtilisateurRepository;
import com.solution.express.services.CotisationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cotisation")
public class CotisationController {


    @Autowired
    private CotisationService cotisationService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CotisationRepository cotisationRepository;

      //Create user
    // @PostMapping("/create/user/{utilisateurId}")
    // @Operation(summary = "Création d'une cotisation")
        @PostMapping("/create")
    public ResponseEntity<Cotisation> createCotisation(
            @Valid @RequestParam("cotisation") String cotisationString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {
        Cotisation cotisation = new Cotisation();
        try {
                // Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                // .orElseThrow(() -> new Exception("Utilisateur non trouvé avec l'ID : " + utilisateurId));
            cotisation = new JsonMapper().readValue(cotisationString, Cotisation.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Cotisation savedCotisation = cotisationService.createCotisation(cotisation, imageFile);

        return new ResponseEntity<>(savedCotisation, HttpStatus.CREATED);
    }

    // @PostMapping("/create")
    // public ResponseEntity<Cotisation> createCotisationWithUser(
    //     @Valid @RequestParam("cotisation") String cotisationString,
    //     @RequestParam(value = "image", required = false) MultipartFile imageFile) {
    //     try {
    //         Cotisation cotisation = new JsonMapper().readValue(cotisationString, Cotisation.class);
    //         Cotisation createdCotisation = cotisationService.createCotisationWithUser(cotisation, imageFile);
    //         return new ResponseEntity<>(createdCotisation, HttpStatus.CREATED);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    
    // public ResponseEntity<Cotisation> createCotisationWithUser(
    // @Valid @RequestBody Cotisation cotisation,
    // @RequestParam(value = "image", required = false) MultipartFile imageFile,
    // @PathVariable("utilisateurId") Integer utilisateurId) {
    // try {
    //     Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
    //             .orElseThrow(() -> new Exception("Utilisateur non trouvé avec l'ID : " + utilisateurId));

    //     Cotisation newCotisation = cotisationService.createCotisation(cotisation, imageFile, utilisateur);

    //     return new ResponseEntity<>(newCotisation, HttpStatus.CREATED);
    // } catch (Exception e) {
    //     return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    // }}
     
    //Mettre à jour un user
      @PutMapping("/update/{id}")
    @Operation(summary = "Mise à jour d'une cotisation par son Id ")
    public ResponseEntity<Cotisation> updateCotisation(
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
     


         @GetMapping("/read")
    //  @Operation(summary = "Affichage de la  liste des utilisateurs")
         public ResponseEntity<List<Cotisation>> getCotisation(){
        return new ResponseEntity<>(cotisationService.getAllCotisation(),HttpStatus.OK);}


       
        @RequestMapping("/addUser/{idCotisation}")
        public ResponseEntity<Cotisation> addUserToCotisation(@PathVariable int idCotisation, @RequestBody Utilisateur utilisateur) throws Exception {
            cotisationService.addUserToCotisation(idCotisation, utilisateur.getIdUtilisateur());
    
            Cotisation cotisation = cotisationRepository.findById(idCotisation).orElseThrow(() -> new Exception("Cotisation introuvable"));
    
            return ResponseEntity.ok(cotisation);
        }


    //Lire un user spécifique
    @GetMapping("/read/{id}")
    public ResponseEntity<?> getCotisationById(@PathVariable Integer id) {
        return cotisationService.findById(id);
    }


    //Recuperer la liste des cotisation creér par un user 
    @GetMapping("/list/{idUtilisateur}")
    @Operation(summary = "affichage des cotisations à travers l'id de utilisateur")
    public ResponseEntity<List<Cotisation>> listeCategorieByUseur(@PathVariable Integer idUtilisateur){
        return  new ResponseEntity<>(cotisationService.getAllCotisationByUtilisateur(idUtilisateur), HttpStatus.OK);
    }

        //Supprimer un utilisateur
    // @Operation(summary = "Supprimer un utilisateur par son ID")
    @DeleteMapping("/delete/{id}")
    public String deleteCotisation(@PathVariable Integer id){
      Optional <Cotisation> cotisation = cotisationRepository.findById(id);
      if (cotisation.isPresent()) {
          cotisationRepository.deleteById(id);
          cotisationService.deleteCotisation(id);  
          return "Super Admin supprimé avec succès.";
      } else {
        
          return "Super Admin non existant avec l'ID " + id;
      }
    }


    //Ajouter user à une cotisation
    // @PostMapping("/addUser")
    // public ResponseEntity<Cotisation> addUserToCotisation(
    //         @RequestBody Cotisation cotisation,
    //         @RequestBody Utilisateur utilisateur) {
    //     try {
    //         Cotisation updatedCotisation = cotisationService.addUserToCotisation(cotisation, utilisateur);
    //         return new ResponseEntity<>(updatedCotisation, HttpStatus.OK);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    //     }
    // }


    
}
