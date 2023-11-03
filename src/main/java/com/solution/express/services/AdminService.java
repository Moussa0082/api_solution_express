package com.solution.express.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solution.express.models.Admin;
import com.solution.express.models.SuperAdmin;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    //Methode pour créer un  admin 
     public ResponseEntity<String> createAdmin(Admin admin) {
      
     adminRepository.save(admin);
     return new ResponseEntity<>("Compte admin créer avec succès", HttpStatus.CREATED);

    }

    //Get user byID
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

    public ResponseEntity<String> disableAdmin(Integer id, Admin admin) {
   
        Optional<Admin> admins = adminRepository.findById(id);
        if(admins.isPresent()){
            admin.setActive(false);
        return new ResponseEntity<>("Admin desactiver avec succès", HttpStatus.OK);
        }
        else{
      return new ResponseEntity<>("Admin non trouver avec l'ID " + id, HttpStatus.BAD_REQUEST);

        }

   }

}
