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
import com.solution.express.models.TypeBanque;
import com.solution.express.services.TypeBanqueService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/typeBanque")
public class TypeBanqueController {
    
    @Autowired
   private TypeBanqueService typeBanqueService;

    //Create banque
    @PostMapping("/create")
    @Operation(summary = "Création type banque")
    public ResponseEntity<TypeBanque> createTypeBanque(
        @Valid @RequestParam("typeBanque") String typeBanqueString,
        @RequestParam(value = "image", required = false) MultipartFile imageFile)
        throws Exception {

    TypeBanque typeBanque = new TypeBanque();
    try {
        typeBanque = new JsonMapper().readValue(typeBanqueString, TypeBanque.class);
    } catch (JsonProcessingException e) {
        throw new Exception(e.getMessage());
    }

    TypeBanque savedTypeBanque = typeBanqueService.createTypeBanque(typeBanque, imageFile);

    return new ResponseEntity<>(savedTypeBanque, HttpStatus.CREATED);
}


     
    //Mettre à jour un user
      @PutMapping("/update/{id}")
    @Operation(summary = "Mise à jour du type d'une  banque par son Id ")
    public ResponseEntity<TypeBanque> updateTypeBanque(
            @PathVariable Integer id,
            @Valid @RequestParam("typeBanque") String typeBanqueString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        TypeBanque typeBanque = new TypeBanque();
        try {
            typeBanque = new JsonMapper().readValue(typeBanqueString, TypeBanque.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            TypeBanque typeBanqueMisAjour = typeBanqueService.modifierTypeBanque(id, typeBanque, imageFile);
            return new ResponseEntity<>(typeBanqueMisAjour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     


         @GetMapping("/read")
     @Operation(summary = "Affichage de la  liste des type banque")
    public ResponseEntity<List<TypeBanque>> getAllTypeBanque(){
        return typeBanqueService.getAllTypeBanque();}



       
    // Lire un banque spécifique
    @GetMapping("/read/{id}")
    public ResponseEntity<?> getTypeBanqueById(@PathVariable Integer id) {
        return typeBanqueService.findById(id);
    }


        //Supprimer un utilisateur
    @DeleteMapping("/delete/{id}")
    // @Operation(summary = "Supprimer un utilisateur par son ID")
    public String delete(@Valid @PathVariable Integer id) {
        return typeBanqueService.deleteTypeBanque(id);
    }

    

}
