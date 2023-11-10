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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.solution.express.models.Admin;
import com.solution.express.models.Agent;
import com.solution.express.models.Demande;
import com.solution.express.models.TypeBanque;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AdminRepository;
import com.solution.express.repository.DemandeRepository;
import com.solution.express.repository.TypeBanqueRepository;
import com.solution.express.repository.UtilisateurRepository;
import com.solution.express.services.DemandeService;
import com.solution.express.services.UtilisateurService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/demandes")
public class DemandeController {


     @Autowired
     private DemandeService demandeService;

     @Autowired
     private UtilisateurRepository utilisateurRepository;

     @Autowired
     private DemandeRepository demandeRepository;

     @Autowired
     private TypeBanqueRepository typeBanqueRepository;

     @Autowired
     private AdminRepository adminRepository;

    //Valider demande
     @PostMapping("/valider/{demandeId}/{agentId}")
    public ResponseEntity<Demande> validateDemande(
            @PathVariable Integer demandeId,
            @PathVariable  Integer agentId
    ) {
        Demande demande = demandeService.validateDemande(demandeId, agentId);
        return ResponseEntity.ok(demande);
    }


    //Rejeter demande
    @PutMapping("/rejeter/{demandeId}/{agentId}")
    public ResponseEntity<Demande> rejeterDemande(
        @PathVariable Integer demandeId,
        @PathVariable Integer agentId
    ) {
       
            Demande rejectedDemande = demandeService.rejectDemande(demandeId, agentId);
            return ResponseEntity.ok(rejectedDemande);
       
    }
    
    //   @PostMapping("/send/{idTypeBanque}/{idUtilisateur}")
    //   public ResponseEntity<Demande> createDemande(
    //     @Valid @RequestParam("demande") String demandeString,
    //     @RequestParam(value = "image1" , required = false) MultipartFile imageFile1,
    //     @RequestParam( value = "image2" , required = false) MultipartFile imageFile2,
    //     @PathVariable("idUtilisateur") Integer idUtilisateur,
    //     @PathVariable( value = "idTypeBanque") Integer idTypeBanque
    // ) throws Exception{
    //     try {
    //         // Récupérer l'utilisateur et le type de banque en fonction des IDs
    //         Utilisateur user = utilisateurRepository.findByIdUtilisateur(idUtilisateur); // UtilisateurService doit être un service que vous avez implémenté
    //         Demande demandes = new Demande();
    //         TypeBanque typeBanque = typeBanqueRepository.findById(idTypeBanque).orElse(null); // TypeBanqueService doit être un service que vous avez implémenté
    //         demandes = new JsonMapper().readValue(demandeString, Demande.class);

    //         Demande createdDemande = demandeService.createDemande(demandes, imageFile1, imageFile2, user, typeBanque);

    //         return new ResponseEntity<>(createdDemande, HttpStatus.CREATED);
    //     } catch (JsonProcessingException e) {
    //         throw new Exception(e.getMessage());
    //     }
    // }

    @PostMapping("/send")
public ResponseEntity<Demande> createDemande(
        @Valid @RequestParam("demande") String demandeString,
        @RequestParam(value = "image1", required = false) MultipartFile imageFile1,
        @RequestParam(value = "image2", required = false) MultipartFile imageFile2) throws Exception {

    // Récupérer l'utilisateur à partir de l'ID de l'utilisateur
    Utilisateur user ;
    try {
        Demande demande = new JsonMapper().readValue(demandeString, Demande.class);
        user = utilisateurRepository.findById(demande.getUtilisateur().getIdUtilisateur())
                .orElseThrow(() -> new Exception("Utilisateur non trouvé"));
       
        // Enregistrer la demande
        Demande savedDemande = demandeService.createDemande(demande, imageFile1, imageFile2, user);
        return new ResponseEntity<>(savedDemande, HttpStatus.CREATED);
    } catch (JsonProcessingException e) {
        throw new Exception(e.getMessage());
    }


}
 ///////////////

    @GetMapping("/list/{idUtilisateur}")
    @Operation(summary = "Affichage la liste  des demandes A travers l'id de l'utilisateur")
    public ResponseEntity<List<Demande>> listeDemandeByUser(@PathVariable Integer idUtilisateur){
        return  new ResponseEntity<>(demandeService.lireParUser(idUtilisateur),HttpStatus.OK);
    }


    // @GetMapping("/read")
    // public ResponseEntity<List<Demande>> getAllDemande() {
    //     return demandeService.getAllDemande();
    // }


       
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
