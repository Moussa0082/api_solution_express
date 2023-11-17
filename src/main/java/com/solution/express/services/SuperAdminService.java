package com.solution.express.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solution.express.Exceptions.NoContentException;
import com.solution.express.models.Admin;
import com.solution.express.models.SuperAdmin;
import com.solution.express.repository.SuperAdminRepository;

@Service
public class SuperAdminService {

    @Autowired
    private SuperAdminRepository superAdminRepository;


    //Methode pour créer un super admin 
     public ResponseEntity<String> createSuperAdmin(SuperAdmin superAdmin) {
         if (superAdminRepository.findByEmail(superAdmin.getEmail()) == null) {
             superAdminRepository.save(superAdmin);

             return new ResponseEntity<>("Compte super admin créer avec succès", HttpStatus.CREATED);
            } else {
                
                return new ResponseEntity<>("Super Admin existant.", HttpStatus.BAD_REQUEST);
         }
     
        
       
    }

    //Modifier super admin methode
    public SuperAdmin updateSuperAdmin(Integer id, SuperAdmin superAdmin) {
        return superAdminRepository.findById(id)
                .map(sa -> {
                    sa.setNom(superAdmin.getNom());
                    sa.setPrenom(superAdmin.getPrenom());
                    sa.setEmail(superAdmin.getEmail());
                    sa.setMotDePasse(superAdmin.getMotDePasse());
                    return superAdminRepository.save(sa);
                }).orElseThrow(() -> new RuntimeException(("Super Admin non existant avec l'ID " + id)));
    
    }
    



    //Recuperer la liste des superAdmins
      public ResponseEntity<List<SuperAdmin>> getAllSuperAdmin() {
     
       try {
            return new ResponseEntity<>(superAdminRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
             e.printStackTrace();
        }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

      //suppression d'un super admin specifique 
    public String deleteSuperAdmin(Integer id) {
        Optional <SuperAdmin> superadmin = superAdminRepository.findById(id);
         if (superadmin.isPresent()) {
             superAdminRepository.deleteById(id);
             return "Super Admin supprimé avec succès.";
         } else {
           
             return "Super Admin non existant.";
         }
     }

    //Verifier si le super admin existe avec le meme email
     public boolean emailExisteDeja(String email) {
        return superAdminRepository.existsByEmail(email);
    }

     //Se connecter 
      public SuperAdmin connexionSuperAdmin(String email, String motdepasse){
        SuperAdmin superAdmin = superAdminRepository.findByMotDePasseAndEmail(motdepasse, email);
        if(superAdmin == null)
        {
            throw new NoContentException("Connexion échoué!");
        }
       
         return superAdmin;
    }
    
    
}
