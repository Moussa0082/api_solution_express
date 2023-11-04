package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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


    //Methode pour créer une banque 
     public TypeBanque createTypeBanque(TypeBanque typeBanque, MultipartFile imageFile) throws Exception {
        if (typeBanqueRepository.findTypeBanqueByNom(typeBanque.getNom()) == null) {
    
            // Traitement du fichier image
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
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
    
            return typeBanqueRepository.save(typeBanque);
        } else {
            throw new IllegalArgumentException("Le type banque " + typeBanque.getNom() + " existe déjà dans le type de la banque " + typeBanque.getBanque().getNom());
        }
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
      public TypeBanque updateTypeBanque(Integer id, TypeBanque typeBanque, MultipartFile imageFile) throws Exception {
        try {
            TypeBanque typeBanqueExistant = typeBanqueRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Type Banque non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            typeBanqueExistant.setNom(typeBanque.getNom());
            typeBanqueExistant.setDescription(typeBanque.getDescription());

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                typeBanqueExistant.setImage("http://localhost/solution\\express\\images" + nomImage);
            }
            


            // Enregistrer le type de la banque mise à jour
            return typeBanqueRepository.save(typeBanqueExistant);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour du type de la banque avec l'ID : " + id);

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
