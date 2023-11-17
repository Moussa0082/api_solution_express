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

import com.solution.express.Exceptions.NoContentException;
import com.solution.express.models.Admin;
import com.solution.express.models.Agent;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    //créer un admin
      public Admin createAdmin(Admin admin, MultipartFile imageFile) throws Exception {
        if (adminRepository.findByEmail(admin.getEmail()) == null) {
    
            // Traitement du fichier image
            if (imageFile != null) {
                String imageLocation = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                try {
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)) {
                        Files.createDirectories(imageRootLocation);
                    }
    
                    String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    admin.setImage("http://localhost/solution/" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
             return  adminRepository.save(admin);
           

        } else {
            throw new IllegalArgumentException("L'admin avec l'amail " + admin.getEmail() + " existe déjà");
        }
    }

    //Get Admin byID
    public ResponseEntity<?> findById(Integer id) {

    Optional<Admin> admin = adminRepository.findById(id);

    if (admin.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'admin avec l'ID " + id + " n'existe pas");
    }

    return ResponseEntity.ok(admin.get());     
   }

    //verifier si l'email existe deja
    public boolean emailExisteDeja(String email) {
        return adminRepository.existsByEmail(email);
    }

     //Modifier  admin methode

     public Admin updateAdmin(Integer id, Admin admin, MultipartFile imageFile) throws Exception {
        try {
            Admin adminExistant = adminRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Admin non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            adminExistant.setNom(admin.getNom());
            adminExistant.setPrenom(admin.getPrenom());
            adminExistant.setEmail(admin.getEmail());
            adminExistant.setMotDePasse(admin.getMotDePasse());
            

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                // String emplacementImage = "C:\\xampp\\htdocs\\solution_express";
                String emplacementImage = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                // adminExistant.setImage("http://localhost/solution_express\\images" + nomImage);
                adminExistant.setImage("http://localhost/solution/" + nomImage);
            }

            // Enregistrer le user mise à jour
            return adminRepository.save(adminExistant);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de l'admin avec l'ID : " + id);

        } 
    }

     //Recuperer la liste des Admins
     public List<Admin> getAllAdmin(){

        List<Admin> admin = adminRepository.findAll();
        if (admin.isEmpty())
        {
            throw new NoContentException("Aucun admin trouvé");
        }
        return admin;
    }

    //Desactiver un admin

    public ResponseEntity<String> disableAdmin(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            admin.get().setIsActive(false);
            adminRepository.save(admin.get());
            return new ResponseEntity<>("L'admin " + admin.get().getPrenom() + " " + admin.get().getNom() + " a été désactivé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Admin non trouvé avec l'ID " + id, HttpStatus.BAD_REQUEST);
        }
    }
   
    //activer admin
    public ResponseEntity<String> enableAdmin(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            admin.get().setIsActive(true);
            adminRepository.save(admin.get());
            return new ResponseEntity<>("L'admin " + admin.get().getPrenom() + " " + admin.get().getNom() + " a été activé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Admin non trouvé avec l'ID " + id, HttpStatus.BAD_REQUEST);
        }
    }
  
      //Supprimer agent
     public String deleteAdmin(Integer id) {
        Optional <Admin> admin = adminRepository.findById(id);
         if (admin.isPresent()) {
             adminRepository.deleteById(id);
             return "Admin supprimé avec succès.";
         } else {
           
             return "Admin non existant.";
         }
     }


     //Se connecter 
      public Admin connexionAdmin(String email, String motdepasse){
        Admin admin = adminRepository.findByMotDePasseAndEmail(motdepasse, email);
        if(admin == null)
        {
            throw new NoContentException("Connexion échoué!");
        }
        if(admin.getIsActive()==false)
        {
            throw new NoContentException("Connexion échoué votre compte  été desactivé par l'administrateur!");
        }
         return admin;
    }


}
