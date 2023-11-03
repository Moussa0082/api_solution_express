package com.solution.express.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solution.express.models.Admin;
import com.solution.express.models.SuperAdmin;
import com.solution.express.repository.AdminRepository;
import com.solution.express.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody Admin admin){

         String mail = admin.getEmail();
         if(adminService.emailExisteDeja(mail)){
    
        return new ResponseEntity<>("L' email " + mail + " existe déjà , essayer un autre email.", HttpStatus.UNAUTHORIZED);
          
         }
       
       return adminService.createAdmin(admin);
        
    }

     //Modifier superAdmin
    @PutMapping("/update/{id}")
    public Admin UpdateAdmin(@PathVariable Integer id, @RequestBody Admin admin){
        return adminService.updateAdmin(id, admin);
    }

    // Get Liste des super admin
      @GetMapping("/read")
    public ResponseEntity<List<Admin>> getAllAdmin() {
        return adminService.getAllAdmin();
    }

   
    @PutMapping("/disable/{id}")
    //Desactiver un admin methode
    public ResponseEntity <String> disableAdmin(@PathVariable Integer id, Admin admin){
    
        adminService.disableAdmin(id, admin);
        return new ResponseEntity<>("Admin desactiver avec succes", HttpStatus.ACCEPTED);
    }



    
}
