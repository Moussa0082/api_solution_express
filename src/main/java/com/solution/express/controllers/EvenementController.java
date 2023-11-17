package com.solution.express.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.Response;
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


import com.solution.express.models.Evenement;

import com.solution.express.repository.CotisationRepository;
import com.solution.express.repository.EvenementRepository;
import com.solution.express.services.EvenementService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/event")
public class EvenementController {

    @Autowired
    private EvenementService evenementService;


    @Autowired
    private EvenementRepository evenementRepository;

    
    @PostMapping("/create/{idCotisation}")
    public Evenement createEvenementWithCotisation(@RequestBody Evenement evenement,@PathVariable Integer idCotisation) {
            // Vérifier si l'événement existe déjà
    
        return evenementService.createEvenementWithCotisation(evenement, idCotisation);
    }



     @GetMapping("/list/{idCotisation}")
    @Operation(summary = "Affichage la liste  des evenements  A travers l'id de la cotisation")
    public ResponseEntity<List<Evenement>> listeEvenementParCotisation(@PathVariable Integer idCotisation){
        return  new ResponseEntity<>(evenementService.getAllEvenemenetByCotisation(idCotisation),HttpStatus.OK);
    }

   

    @GetMapping("/read/{id}")
    public ResponseEntity<?> getEvenementById(@PathVariable Integer id) {
        return evenementService.findById(id);
    }


      @PutMapping("/update/{id}")
    public Evenement updateEvenement(@PathVariable Integer id, @RequestBody Evenement evenement){
        return evenementService.updateEvenement(id, evenement);
    }

      

    // Get Liste des super admin
      @GetMapping("/read")
    public ResponseEntity<List<Evenement>> getAllEvenement() {
        return evenementService.getAllEvenement();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSuperAdmin(@PathVariable Integer id){
      Optional <Evenement> evenement = evenementRepository.findById(id);
      if (evenement.isPresent()) {
          evenementRepository.deleteById(id);
          evenementService.deleteEvenement(id);
          return "Evenement supprimé avec succès.";
      } else {
        
          return " Evenement non existant avec l'ID " + id;
      }
    }

  

}
