package com.solution.express.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.solution.express.models.Demande;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.UtilisateurRepository;
import com.solution.express.services.DemandeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/demandes")
public class DemandeController {


     @Autowired
     private DemandeService demandeService;

     @Autowired
     private UtilisateurRepository utilisateurRepository;



//       @PostMapping("/send/{idUtilisateur}")
//        public ResponseEntity<Demande> createDemande(
//         @Valid @RequestParam("demande") String demandeString,
//         @RequestParam(value = "image1", required = false) MultipartFile imageFile1,
//         @RequestParam(value = "image2", required = false) MultipartFile imageFile2,
//         @PathVariable("idUtilisateur") int idUtilisateur) throws Exception {

//     // Récupérer l'utilisateur à partir de l'ID de l'utilisateur
//     Utilisateur user = utilisateurRepository.findById(idUtilisateur)
//             .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouver: " + idUtilisateur));
//              Demande demande = new Demande();
//         try {
//             demande = new JsonMapper().readValue(demandeString, Demande.class);
//         } catch (JsonProcessingException e) {
//             throw new Exception(e.getMessage());
//         }


//     Demande savedDemande = demandeService.createDemande(demande, imageFile1, imageFile2, user);

//     return new ResponseEntity<>(savedDemande, HttpStatus.CREATED);
// }


     @GetMapping("/read")
    //  @Operation(summary = "Affichage de la  liste des utilisateurs")
    public ResponseEntity<List<Demande>> getDemande(){
        return new ResponseEntity<>(demandeService.getAllDemande(),HttpStatus.OK);}


       
    //Lire un user spécifique
    @GetMapping("/read/{id}")
    public ResponseEntity<?> getDemandeById(@PathVariable Integer id) {
        return demandeService.findById(id);
    }


        //Supprimer un utilisateur
     @DeleteMapping("/delete/{id}")
    // @Operation(summary = "Supprimer un utilisateur par son ID")
    public String delete(@Valid @PathVariable Integer id) {
        return demandeService.deleteDemande(id);
    }

    
}
