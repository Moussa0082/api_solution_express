package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.models.Banque;
import com.solution.express.models.TypeBanque;
import com.solution.express.repository.BanqueRepository;
import com.solution.express.repository.TypeBanqueRepository;

@Service
public class TypeBanqueService {


     @Autowired
    private TypeBanqueRepository typeBanqueRepository;

    @Autowired
    private BanqueRepository banqueRepository;

    
    public TypeBanque createTypeBanque(TypeBanque typeBanque, MultipartFile imageFile) throws Exception {

        // Verifie si la banque existe
        Banque banque = banqueRepository.findById(typeBanque.getBanque().getIdBanque())
                .orElseThrow(() -> new IllegalArgumentException("Banque non trouver: " + typeBanque.getBanque().getIdBanque()));
    
        //Verifie si le type de la banque existe dans la banque en question pour quil nai pas de doublons de types dans la meme banque
        Optional<TypeBanque> existingTypeBanque = typeBanqueRepository.findByNomAndBanque(typeBanque.getNom(), banque);
        if (existingTypeBanque.isPresent()) {
            throw new IllegalArgumentException("Le type banque " + typeBanque.getNom() + " existe déjà à la banque " + banque.getNom());
        }
    
        // Traitement image
        if (imageFile != null) {
            String imageLocation = "C:\\xampp\\htdocs\\solution_express";
            try {
                Path imageRootLocation = Paths.get(imageLocation);
                if (!Files.exists(imageRootLocation)) {
                    Files.createDirectories(imageRootLocation);
                }
    
                String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path imagePath = imageRootLocation.resolve(imageName);
                Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                typeBanque.setImage("http://localhost/solution_express\\images" + imageName);
            } catch (IOException e) {
                throw new Exception("Error processing the image file: " + e.getMessage());
            }
        }
    
        // Ici je capte la date actuel et le stock dans date de creation
        LocalDate localDate = LocalDate.now();
        typeBanque.setDateCreated(localDate.toString());
    
        // sauvegarde du type de la banque
        return typeBanqueRepository.save(typeBanque);
    }
    

    //get users byId
    public ResponseEntity<?> findById(Integer id) {

        Optional<TypeBanque> typeBanque = typeBanqueRepository.findById(id);
    
        if (typeBanque.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le Type banque avec l'ID " + id + " n'existe pas");
        }
    
        return ResponseEntity.ok(typeBanque.get());     
       }


    //Modifier un user
    public TypeBanque modifierTypeBanque(Integer id, TypeBanque typeBanque, MultipartFile imageFile) throws Exception {
        try {
            TypeBanque typeBanqueExistant = typeBanqueRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Type de banque non trouvé avec l'ID: " + id));
    
            // Vérifier si la banque existe
            Banque banque = banqueRepository.findById(typeBanque.getBanque().getIdBanque())
                    .orElseThrow(() -> new IllegalArgumentException("Banque non trouvée: " + typeBanque.getBanque().getIdBanque()));
    
            // Vérifier si le type de banque existe dans la banque en question pour éviter les doublons de types dans la même banque
            Optional<TypeBanque> typeBanqueExistantAvecMemeNom = typeBanqueRepository.findByNomAndBanque(typeBanque.getNom(), banque);
            if (typeBanqueExistantAvecMemeNom.isPresent() && typeBanqueExistantAvecMemeNom.get().getIdTypeBanque() != typeBanqueExistant.getIdTypeBanque()) {
                throw new IllegalArgumentException("Le type de banque " + typeBanque.getNom() + " existe déjà à la banque " + banque.getNom());
            }
    
            // Mettre à jour les champs
            typeBanqueExistant.setNom(typeBanque.getNom());
            typeBanqueExistant.setDescription(typeBanque.getDescription());
    
            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\xampp\\htdocs\\solution_express";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);
    
                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                typeBanqueExistant.setImage("http://localhost/solution\\express\\images" + nomImage);
            }
    
            // Enregistrer le type de banque mis à jour
            return typeBanqueRepository.save(typeBanqueExistant);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour du type de banque avec l'ID : " + id);
        }
    }



    //Recuperer la liste des banque
      public ResponseEntity<List<TypeBanque>> getAllTypeBanque() {
     
       try {
            return new ResponseEntity<>(typeBanqueRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
             e.printStackTrace();
        }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

      //suppression d'une banque specifique 
    public String deleteTypeBanque(Integer id) {
        Optional <TypeBanque> typeBanque = typeBanqueRepository.findById(id);
         if (typeBanque.isPresent()) {
             typeBanqueRepository.deleteById(id);
             return "Type Banque supprimé avec succès.";
         } else {
           
             return "Type Banque non existant.";
         }
     }
    
    
}
