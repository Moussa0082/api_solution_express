package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.models.Demande;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.DemandeRepository;

@Service
public class DemandeService {

    @Autowired
     private DemandeRepository demandeRepository;

    // String msg_a = "Votre tentative de dépense de " + montantDepense +
    // " Fcfa a été annulée car \nelle est supérieur au montant de votre budget actuel " + budgets.getMont_bud() + " Fcfa."  + "\n"+
    // " Veuillez ajuster vos dépenses en conséquence.";
    //      EmailDetails details = new EmailDetails(depense.getUser().getEmail(), msg_a, "Annulation de Dépense");
    //  emailServiceImpl.sendSimpleMail(details);
    //     return "Le montant de la dépense ne doit pas dépasser celui du budget.";


    // public Demande createDemande(Demande demande, MultipartFile imageFile1, MultipartFile imageFile2, Utilisateur user) throws Exception {

    //     // Générer un numéro de demande aléatoire
    //     int numeroDemande = (int) (Math.random() * 999) + 1;
    //     String numeroDemandeFormate = String.format("%03d", numeroDemande);
    //     while (demandeRepository.findByNumeroDemande(numeroDemandeFormate) != null) {
    //         numeroDemande = (int) (Math.random() * 999) + 1;
    //         numeroDemandeFormate = String.format("%03d", numeroDemande);
    //     }
    
    //     // Vérifier si une demande du même type existe déjà
    //     List<Demande> demandesDuMemeType = demandeRepository.findByTypeBanque(demande.getTypeBanque());
    //     for (Demande demandeDuMemeType : demandesDuMemeType) {
    //         if (demandeDuMemeType.getNumeroDemande().equals(numeroDemandeFormate)) {
    //             throw new IllegalArgumentException("Une demande de ce type existe déjà.");
    //         }
    //     }
    
    //     demande.setNumeroDemande(numeroDemandeFormate);
    
    //     // Traitement du fichier image 1
    //     if (imageFile1 != null) {
    //         String imageLocation = "C:\\xampp\\htdocs\\solution_express";
    //         try {
    //             Path imageRootLocation = Paths.get(imageLocation);
    //             if (!Files.exists(imageRootLocation)) {
    //                 Files.createDirectories(imageRootLocation);
    //             }
    
    //             String imageName1 = UUID.randomUUID().toString() + "_" + imageFile1.getOriginalFilename();
    //             Path imagePath1 = imageRootLocation.resolve(imageName1);
    //             Files.copy(imageFile1.getInputStream(), imagePath1, StandardCopyOption.REPLACE_EXISTING);
    //             demande.setImage1("http://localhost/solution_express\\images" + imageName1);
    //         } catch (IOException e) {
    //             throw new Exception("Erreur lors du traitement du fichier image 1 : " + e.getMessage());
    //         }
    //     }
    
    //     // Traitement du fichier image 2
    //     if (imageFile2 != null) {
    //         String imageLocation = "C:\\xampp\\htdocs\\solution_express";
    //         try {
    //             Path imageRootLocation = Paths.get(imageLocation);
    //             if (!Files.exists(imageRootLocation)) {
    //                 Files.createDirectories(imageRootLocation);
    //             }
    
    //             String imageName2 = UUID.randomUUID().toString() + "_" + imageFile2.getOriginalFilename();
    //             Path imagePath2 = imageRootLocation.resolve(imageName2);
    //             Files.copy(imageFile2.getInputStream(), imagePath2, StandardCopyOption.REPLACE_EXISTING);
    //             demande.setImage2("http://localhost/solution_express\\images" + imageName2);
    //         } catch (IOException e) {
    //             throw new Exception("Erreur lors du traitement du fichier image 2 : " + e.getMessage());
    //         }
    //     }
    
    //     // Set the current date and time to the demande's dateCreated and heureCreated fields
    //     LocalDate localDate = LocalDate.now();
    //     LocalTime localTime = LocalTime.now();
    //     demande.setDateCreated(localDate);
    //     demande.setHeureCreated(localTime);
    
    //     // Set the user's information to the demande's user fields
    //     demande.setUser(user);
    //     demande.setNom(user.getNom());
    //     demande.setPrenom(user.getPrenom());
    //     demande.setEmail(user.getEmail());
    //     demande.setPhone(user.getPhone());
    //     demande.setAdresse(user.getAdresse());
    
    //     // Save the demande to the database
    //     demande = demandeRepository.save(demande);}

    
}
