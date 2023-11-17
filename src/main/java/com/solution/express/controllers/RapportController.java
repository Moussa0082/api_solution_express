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
import org.springframework.web.bind.annotation.RestController;

import com.solution.express.models.Rapport;
import com.solution.express.repository.RapportRepository;
import com.solution.express.services.RapportService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/rapport")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @Autowired
    private RapportRepository rapportRepository;


     @PostMapping("/create/{idCotisation}")
    public Rapport createRapportWithCotisation(@RequestBody Rapport rapport,@PathVariable Integer idCotisation) {
    
        return rapportService.createRapportWithCotisation(rapport, idCotisation);
    }

     @GetMapping("/list/{idCotisation}")
    @Operation(summary = "Affichage la liste  des rapport  A travers l'id de la cotisation")
    public ResponseEntity<List<Rapport>> listeRapportParCotisation(@PathVariable Integer idCotisation){
        return  new ResponseEntity<>(rapportService.getAllRapportByCotisation(idCotisation),HttpStatus.OK);
    }

   

    @GetMapping("/read/{id}")
    public ResponseEntity<?> getRapportById(@PathVariable Integer id) {
        return rapportService.findById(id);
    }


      @PutMapping("/update/{id}")
    public Rapport updateRapport(@PathVariable Integer id, @RequestBody Rapport rapport){
        return rapportService.updateRapport(id, rapport);
    }

      

    // Get Liste des super admin
      @GetMapping("/read")
    public ResponseEntity<List<Rapport>> getAllRapport() {
        return rapportService.getAllRapport();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRapport(@PathVariable Integer id){
      Optional <Rapport> rapport = rapportRepository.findById(id);
      if (rapport.isPresent()) {
          rapportRepository.deleteById(id);
          rapportService.deleteRapport(id);
          return "Rapport supprimé avec succès.";
      } else {
        
          return " Rapport non existant avec l'ID " + id;
      }
    }

    
}
