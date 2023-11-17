package com.solution.express.controllers;

import java.util.List;
import java.util.Optional;

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

import com.solution.express.models.Admin;
import com.solution.express.models.SuperAdmin;
import com.solution.express.repository.SuperAdminRepository;
import com.solution.express.services.SuperAdminService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/superAdmin")
public class SuperAdminController {

  @Autowired
  private SuperAdminService superAdminService;

  @Autowired
    private SuperAdminRepository superAdminRepository;


   @PostMapping("/create")
    public ResponseEntity<String> createSuperAdmin(@RequestBody SuperAdmin superAdmin){

         String mail = superAdmin.getEmail();
         if(superAdminService.emailExisteDeja(mail)){
    
        return new ResponseEntity<>("L' email " + mail + " existe déjà , essayer un autre email.", HttpStatus.UNAUTHORIZED);
          
         }
       
       return superAdminService.createSuperAdmin(superAdmin);
        
    }

    //Modifier superAdmin
    @PutMapping("/update/{id}")
    public SuperAdmin UpdateSuperAdmin(@PathVariable Integer id, @RequestBody SuperAdmin superAdmin){
        return superAdminService.updateSuperAdmin(id, superAdmin);
    }

    // Get Liste des super admin
      @GetMapping("/read")
    public ResponseEntity<List<SuperAdmin>> getAllUser() {
        return superAdminService.getAllSuperAdmin();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSuperAdmin(@PathVariable Integer id){
      Optional <SuperAdmin> superadmin = superAdminRepository.findById(id);
      if (superadmin.isPresent()) {
          superAdminRepository.deleteById(id);
          superAdminService.deleteSuperAdmin(id);  
          return "Super Admin supprimé avec succès.";
      } else {
        
          return "Super Admin non existant avec l'ID " + id;
      }
    }


    //Se connecter 
           @GetMapping("/login")
           @Operation(summary = "Connexion d'un Admin ")
           public SuperAdmin connexion(@RequestParam("email")  String email,
                                   @RequestParam("motDePasse")  String motdepasse) {
               return superAdminService.connexionSuperAdmin(email, motdepasse);
           }

    
}
