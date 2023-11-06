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
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.models.Banque;
import com.solution.express.models.SuperAdmin;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.BanqueRepository;
import com.solution.express.repository.SuperAdminRepository;

@Service
public class BanqueService {


     @Autowired
    private BanqueRepository banqueRepository;


    //Methode pour créer une banque 
     public Banque createBanque(Banque banque, MultipartFile imageFile) throws Exception {
        if (banqueRepository.findBanqueByNom(banque.getNom()) == null) {
    
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
                    banque.setImage("http://localhost/solution_express\\images" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
            LocalDate localDate  =  LocalDate.now();
            banque.setDateCreated(localDate.toString());
        
    
            return banqueRepository.save(banque);
        } else {
            throw new IllegalArgumentException("La banque " + banque.getNom() + " existe déjà");
        }
    }
    

    //get users byId
    public ResponseEntity<?> findById(Integer id) {

        Optional<Banque> banque = banqueRepository.findById(id);
    
        if (banque.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La banque avec l'ID " + id + " n'existe pas");
        }
    
        return ResponseEntity.ok(banque.get());     
       }

    //Modifier un user
      public Banque updateBanque(Integer id, Banque banque, MultipartFile imageFile) throws Exception {
        try {
            Banque banqueExistant = banqueRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Banque non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            banqueExistant.setNom(banque.getNom());
            banqueExistant.setAdresse(banque.getAdresse());
            

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                String emplacementImage = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                banqueExistant.setImage("http://localhost/solution\\express\\images" + nomImage);
            }
            
            // Enregistrer la banque mise à jour
            return banqueRepository.save(banqueExistant);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de la banque avec l'ID : " + id);

        } 
    }




    //Recuperer la liste des banque
      public ResponseEntity<List<Banque>> getAllBanque() {
     
       try {
            return new ResponseEntity<>(banqueRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
             e.printStackTrace();
        }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

      //suppression d'une banque specifique 
    public String deleteBanque(Integer id) {
        Optional <Banque> banque = banqueRepository.findById(id);
         if (banque.isPresent()) {
             banqueRepository.deleteById(id);
             return "Banque supprimé avec succès.";
         } else {
           
             return "Banque non existant.";
         }
     }
    
}
