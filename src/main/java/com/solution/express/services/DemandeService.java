package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.Exceptions.NoContentException;
import com.solution.express.models.Agent;
import com.solution.express.models.Alerte;
import com.solution.express.models.Demande;
import com.solution.express.models.TypeBanque;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AlerteRepository;
import com.solution.express.repository.DemandeRepository;
import com.solution.express.repository.TypeBanqueRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DemandeService {

    @Autowired
     private DemandeRepository demandeRepository;

     @Autowired
     private EmailService emailService;

     @Autowired
     private AlerteRepository alerteRepository;

     @Autowired
     private TypeBanqueRepository typeBanqueRepository;

    // String msg_a = "Votre tentative de dépense de " + montantDepense +
    // " Fcfa a été annulée car \nelle est supérieur au montant de votre budget actuel " + budgets.getMont_bud() + " Fcfa."  + "\n"+
    // " Veuillez ajuster vos dépenses en conséquence.";
    //      EmailDetails details = new EmailDetails(depense.getUser().getEmail(), msg_a, "Annulation de Dépense");
    //  emailServiceImpl.sendSimpleMail(details);
    //     return "Le montant de la dépense ne doit pas dépasser celui du budget.";


    

    public Demande createDemande(Demande demande, MultipartFile imageFile1, MultipartFile imageFile2, Utilisateur user) throws Exception {

        // Générer un numéro de demande aléatoire
        String numeroDemande = generateRandomId();
        // Modifier le format du numéro de demande pour qu'il contienne trois chiffres et trois lettres
        numeroDemande = numeroDemande.substring(0, 3) + numeroDemande.substring(3, 6);
        while (demandeRepository.findByNumeroDemande(numeroDemande) != null) {
            numeroDemande = generateRandomId();
            numeroDemande = numeroDemande.substring(0, 3) + numeroDemande.substring(3, 6);
        }
    
        // Vérifier si une demande du même type existe déjà pour cet utilisateur
            
        Demande existingDemande = demandeRepository.findByTypeBanqueAndUtilisateur(demande.getTypeBanque(), user);
        if (existingDemande != null) {
            throw new IllegalArgumentException("Une demande de ce type existe déjà pour l'utilisateur avec l'email" + demande.getUtilisateur().getEmail());
        }
    
        demande.setNumeroDemande(numeroDemande);
    
        // Traitement du fichier image 1
        if (imageFile1 != null) {
            String imageLocation = "C:\\xampp\\htdocs\\solution_express";
            try {
                Path imageRootLocation = Paths.get(imageLocation);
                if (!Files.exists(imageRootLocation)) {
                    Files.createDirectories(imageRootLocation);
                }
    
                String imageName1 = UUID.randomUUID().toString() + "_" + imageFile1.getOriginalFilename();
                Path imagePath1 = imageRootLocation.resolve(imageName1);
                Files.copy(imageFile1.getInputStream(), imagePath1, StandardCopyOption.REPLACE_EXISTING);
                demande.setPhotoDidentite("http://localhost/solution/"+imageName1);
            } catch (IOException e) {
                throw new Exception("Erreur lors du traitement du fichier image 1 : " + e.getMessage());
            }
        }
    
        // Traitement du fichier image 2
        if (imageFile2 != null) {
            String imageLocation = "C:\\xampp\\htdocs\\solution_express";
            try {
                Path imageRootLocation = Paths.get(imageLocation);
                if (!Files.exists(imageRootLocation)) {
                    Files.createDirectories(imageRootLocation);
                }
    
                String imageName2 = UUID.randomUUID().toString() + "_" + imageFile2.getOriginalFilename();
                Path imagePath2 = imageRootLocation.resolve(imageName2);
                Files.copy(imageFile2.getInputStream(), imagePath2, StandardCopyOption.REPLACE_EXISTING);
                demande.setPhotoValide("http://localhost/solution/"+ imageName2);
            } catch (IOException e) {
                throw  new Exception("Erreur lors du traitement du fichier image 2 : " + e.getMessage());
            }
        }
    
        // Set the current date and time to the demande's dateCreated and heureCreated fields
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        demande.setDateDemande(localDate.toString());
        demande.setHeureDemande(localTime.toString());
    
        // Set the user's information to the demande's user fields
        demande.setUtilisateur(user);

        String dateDemande;
        LocalDate localDate2 = LocalDate.now();
        dateDemande = localDate2.toString();
        // Save the demande to the database
        Demande savedDemande = demandeRepository.save(demande);
        String msg_a = "Votre tentative demande de " + demande.getTypeBanque().getNom() +
                " a été envoyée avec succès " + "\n" +
                " Veuillez patienter le temps qu'un de nos agents s'occupe de traitement \n de votre demande.";
        Alerte alerte = new Alerte(0, demande.getUtilisateur().getEmail(), msg_a, "Alerte reception de demande", dateDemande, user);
              
             emailService.sendSimpleMail(alerte);
    
             alerteRepository.save(alerte);
    
        // Envoyer une alerte de succès
        // Utilisez un service d'alerte pour envoyer une notification à l'utilisateur.
         return savedDemande;

    }

     private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomId() {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
    

    // public Demande createDemande(Demande demande, MultipartFile imageFile1, MultipartFile imageFile2, Utilisateur user) throws Exception {

    //     // Générer un numéro de demande aléatoire
    //     int numeroDemande = (int) (Math.random() * 999) + 1;
    //     String numeroDemandeFormate = String.format("%03d", numeroDemande);
    //     while (demandeRepository.findByNumeroDemande(numeroDemandeFormate) != null) {
    //         numeroDemande = (int) (Math.random() * 999) + 1;
    //         numeroDemandeFormate = String.format("%03d", numeroDemande);
    //     }
    
    //     // Vérifier si une demande du même type existe déjà pour cet utilisateur
        
    //     Demande existingDemande = demandeRepository.findByTypeBanqueAndUtilisateur(demande.getTypeBanque(), user);
    //     if (existingDemande != null) {
    //         throw new IllegalArgumentException("Une demande de ce type existe déjà pour cet utilisateur.");
    //     }
    
    //     demande.setNumeroDemande(numeroDemandeFormate);
    
    //     // Traitement du fichier image 1
    //        if (imageFile1 != null) {
    //         String imageLocation = "C:\\xampp\\htdocs\\solution_express";
    //         try {
    //             Path imageRootLocation = Paths.get(imageLocation);
    //             if (!Files.exists(imageRootLocation)) {
    //                 Files.createDirectories(imageRootLocation);
    //             }
    
    //             String imageName1 = UUID.randomUUID().toString() + "_" + imageFile1.getOriginalFilename();
    //             Path imagePath1 = imageRootLocation.resolve(imageName1);
    //             Files.copy(imageFile1.getInputStream(), imagePath1, StandardCopyOption.REPLACE_EXISTING);
    //             demande.setPhotoDidentite(numeroDemandeFormate);
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
    //             demande.setPhotoValide(numeroDemandeFormate);
    //         } catch (IOException e) {
    //             throw new Exception("Erreur lors du traitement du fichier image 2 : " + e.getMessage());
    //         }
    //     }
    
    //     // Set the current date and time to the demande's dateCreated and heureCreated fields
    //     LocalDate localDate = LocalDate.now();
    //     LocalTime localTime = LocalTime.now();
    //     demande.setDateDemande(localDate.toString());
    //     demande.setHeureDemande(localTime.toString());
    
    //     // Set the user's information to the demande's user fields
    //     // demande.setUtilisateur(user);
    
    //     // Save the demande to the database
    //     Demande savedDemande = demandeRepository.save(demande);
    //       String msg_a = "Votre tentative demande  de " + demande.getTypeBanque().getNom() +
    //         " a été envoyé avec succès " + "\n"+
    //         " Veuillez patienter le temps qu'un de nos agent s'occupe de traitement \n de votre demande.";
    //      Alerte alerte = new  Alerte(numeroDemande, demande.getUtilisateur().getEmail(), msg_a, "Alerte Reception de demande", numeroDemandeFormate, user);
    //       emailService.sendSimpleMail(alerte);

    //       alerteRepository.save(alerte);
    
    //     // Envoyer une alerte de succès
    //     // Utilisez un service d'alerte pour envoyer une notification à l'utilisateur.
    
    //     return savedDemande;
    // }
    


    //  public List<Demande> getAllDemande(){

    //     List<Demande> demandes = demandeRepository.findAll();
    //     if (demandes.isEmpty())
    //         throw new NoContentException("Aucune demande trouvé");
    //     return demandes;
    // }


    //////////

    // public ResponseEntity<List<Demande>> getAllDemande() {
    //     try {
    //         List<Demande> demandes = demandeRepository.findAll();
    //         return new ResponseEntity<>(demandes, HttpStatus.OK);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    //     }
    // }

    //  public List<Demande> lireParUser(Integer idUtilisateur){
    //     List<Demande> demandes = demandeRepository.findByUtilisateurIdUtilisateur(idUtilisateur);
    //     if (demandes.isEmpty())
    //         throw new EntityNotFoundException("Aucune demande trouvée");
    //     return demandes;

    // }


    //Get user byID
    public ResponseEntity<?> findById(Integer id) {

    Optional<Demande> demande = demandeRepository.findById(id);

    if (demande.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La demande avec l'ID " + id + " n'existe pas");
    }

    return ResponseEntity.ok(demande.get());     
   }
   
        
    
        

    //suppression d'un user specifique 
    public String deleteDemande(Integer id) {
        Optional <Demande> demande = demandeRepository.findById(id);
         if (demande.isPresent()) {
             demandeRepository.deleteById(id);
             return "Demande supprimé avec succès.";
         } else {
           
             return "Demande non existant.";
         }
     }

}