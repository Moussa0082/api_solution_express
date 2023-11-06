package com.solution.express.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.solution.express.models.Admin;
import com.solution.express.models.Agent;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AdminRepository;
import com.solution.express.services.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;


    @Autowired
    private AdminRepository adminRepository;


     @PostMapping("/create")
     public ResponseEntity<Admin> createAdmin(
            @Valid @RequestParam("admin") String adminString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {
                

                Admin admin = new Admin();
                try {
                    admin = new JsonMapper().readValue(adminString, Admin.class);
                } catch (JsonProcessingException e) {
                    throw new Exception(e.getMessage());
                }
            
                // je le cree et le sauvegarde.
                Admin savedAdmin = adminService.createAdmin(admin, imageFile);
            
                return new ResponseEntity<>(savedAdmin, HttpStatus.CREATED);
            }



     //Modifier Admin
    @PutMapping("/update/{id}")
     @Operation(summary = "Mise à jour d'un admin par son Id ")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Integer id,
            @Valid @RequestParam("admin") String adminString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Admin admin = new Admin();
        try {
            admin = new JsonMapper().readValue(adminString, Admin.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Admin adminMisAjour = adminService.updateAdmin(id, admin, imageFile);
            return new ResponseEntity<>(adminMisAjour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Liste des super admin
      @GetMapping("/read")
    public ResponseEntity<List<Admin>> getAllAdmin() {
        return adminService.getAllAdmin();
    }

   
    @PatchMapping("/disable/{id}")
    //Desactiver un admin methode
    public ResponseEntity <String> disableAdmin(@PathVariable Integer id){
    
        adminService.disableAdmin(id);
        return new ResponseEntity<>("Admin desactiver avec succes", HttpStatus.ACCEPTED);
    }

    //Aciver admin
      @PatchMapping("/enable/{id}")
    //Desactiver un admin methode
    public ResponseEntity <String> enableAdmin(@PathVariable Integer id){
    
        adminService.enableAdmin(id);
        return new ResponseEntity<>("Admin activer avec succes", HttpStatus.ACCEPTED);
    }


           //Supprimer un admin
           @DeleteMapping("/delete/{id}")
           public String deleteAgent(@PathVariable Integer id){
             Optional <Admin> admin = adminRepository.findById(id);
             if (admin.isPresent()) {
                 adminRepository.deleteById(id);
                 adminService.deleteAdmin(id);  
                 return " Admin supprimé avec succès.";
             } else {
               
                 return "Admin non existant avec l'ID " + id;
             }
           }

    
}
