package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.Exceptions.NoContentException;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.UtilisateurRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    //créer un user
      public Utilisateur createUtilisateur(Utilisateur utilisateur, MultipartFile imageFile) throws Exception {
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) == null) {
    
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
                    utilisateur.setImage("http://localhost/solution_express\\images" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
         
    
            return utilisateurRepository.save(utilisateur);
        } else {
            throw new IllegalArgumentException("L'email " + utilisateur.getEmail() + " existe déjà");
        }
    }
    

    // //get users byId
    // public Utilisateur getUtilisateurById(Integer id) {
    //     return utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Cet utilisateur n'existe pas !"));
    // }


    //Modifier un user
      public Utilisateur updateUtilisateur(Integer id, Utilisateur utilisateur, MultipartFile imageFile) throws Exception {
        try {
            Utilisateur utilisateurExistant = utilisateurRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            utilisateurExistant.setNom(utilisateur.getNom());
            utilisateurExistant.setPrenom(utilisateur.getPrenom());
            utilisateurExistant.setEmail(utilisateur.getEmail());
            // utilisateurExistant.setMotDePasse(utilisateur.getMotDePasse());
            

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                // String emplacementImage = "C:\\xampp\\htdocs\\solution_express";
                String emplacementImage = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                // utilisateurExistant.setImage("http://localhost/solution_express\\images" + nomImage);
                utilisateurExistant.setImage("http://localhost/solution/" + nomImage);
            }

            // Enregistrer le user mise à jour
            return utilisateurRepository.save(utilisateurExistant);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de l'utilisateur avec l'ID : " + id);

        } 
    }


    //recuperer la liste des ueser
        public List<Utilisateur> getAllUtilisateur(){

        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        if (utilisateurs.isEmpty())
            throw new NoContentException("Aucun utilisateur trouvé");
        return utilisateurs;
    }



    //Get user byID
    public ResponseEntity<?> findById(Integer id) {

    Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);

    if (utilisateur.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'utilisateur avec l'ID " + id + " n'existe pas");
    }

    return ResponseEntity.ok(utilisateur.get());     
   }
   
        
    //suppression d'un user specifique 
    public String deleteUtilisateur(Integer id) {
        Optional <Utilisateur> utiliateur = utilisateurRepository.findById(id);
         if (utiliateur.isPresent()) {
             utilisateurRepository.deleteById(id);
             return "Utilisateur supprimé avec succès.";
         } else {
           
             return "Utilisateur non existant.";
         }
     }

    //Methode se connecter
    // public Utilisateur connectionUtilisateur(String email, String motDePasse) {
    //     // Utilisateur utilisateur;
    //     // Utilisateur user = utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
    //     // if (user == null) {
    //     //     throw new EntityNotFoundException("Cet utilisateur n'existe pas");
    //     // }
    //     // // if(utilisateur!= null){
    //     // //    String nom = utilisateur.getEmail();
    //     // //    String mail = utilisateur.getEmail();
    //     // // }

    //     // return user;
    // }
    
}
