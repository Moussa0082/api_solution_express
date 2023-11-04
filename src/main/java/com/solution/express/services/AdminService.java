package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.models.Admin;
import com.solution.express.models.SuperAdmin;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    //Methode pour créer un  admin 
    //  public ResponseEntity<String> createAdmin(Admin admin) {
      
    //  adminRepository.save(admin);
    //  return new ResponseEntity<>("Compte admin créer avec succès", HttpStatus.CREATED);

    // }

    //créer un admin
      public Admin createAdmin(Admin admin, MultipartFile imageFile) throws Exception {
        if (adminRepository.findByEmail(admin.getEmail()) == null) {
    
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
                    admin.setImage("http://localhost/solution_express\\images" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
    
             adminRepository.save(admin);
             return admin;

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
    public Admin updateAdmin(Integer id, Admin admin) {
        return adminRepository.findById(id)
                .map(ad -> {
                    ad.setNom(admin.getNom());
                    ad.setPrenom(admin.getPrenom());
                    ad.setEmail(admin.getEmail());
                    ad.setMotDePasse(admin.getMotDePasse());
                    return adminRepository.save(ad);
                }).orElseThrow(() -> new RuntimeException(("Admin non existant avec l'ID " + id)));
    
    }

     //Recuperer la liste des Admins
      public ResponseEntity<List<Admin>> getAllAdmin() {
     
       try {
            return new ResponseEntity<>(adminRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
             e.printStackTrace();
        }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    //Desactiver un admin

    public ResponseEntity<String> disableAdmin(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            admin.get().setActive(false);
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
            admin.get().setActive(true);
            adminRepository.save(admin.get());
            return new ResponseEntity<>("L'admin " + admin.get().getPrenom() + " " + admin.get().getNom() + " a été activé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Admin non trouvé avec l'ID " + id, HttpStatus.BAD_REQUEST);
        }
    }

}
