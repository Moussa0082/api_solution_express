package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.Exceptions.BadRequestException;
import com.solution.express.Exceptions.NoContentException;
import com.solution.express.models.Admin;
import com.solution.express.models.Agent;
import com.solution.express.models.Alerte;
import com.solution.express.models.Banque;
import com.solution.express.models.Demande;
import com.solution.express.models.TypeBanque;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AgentRepository;
import com.solution.express.repository.AlerteRepository;
import com.solution.express.repository.BanqueRepository;
import com.solution.express.repository.DemandeRepository;
import com.solution.express.repository.TypeBanqueRepository;
import com.solution.express.repository.UtilisateurRepository;

import aj.org.objectweb.asm.Type;
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

     @Autowired
     private UtilisateurRepository utilisateurRepository;

     @Autowired
     private AgentRepository agentRepository;

     @Autowired
     private BanqueRepository banqueRepository;

  //Faire demande
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
        String dateDemandeR;
        LocalDate localDate2 = LocalDate.now();
        dateDemandeR = localDate2.toString();

        TypeBanque typeBanque = typeBanqueRepository.findById(demande.getTypeBanque().getIdTypeBanque()).orElse(null);
        Demande existingDemande = demandeRepository.findByTypeBanqueAndUtilisateur(demande.getTypeBanque(), user);
        Banque banque = banqueRepository.findById(typeBanque.getBanque().getIdBanque()).orElse(null);    
        
        

        if (existingDemande != null) {
            //Envoi email
            String msg_aa = "Votre  demande de " + existingDemande.getTypeBanque().getNom() + " à la banque "  + existingDemande.getTypeBanque().getBanque().getNom()+
            " a été envoyée déjà , elle est en cours de traitement " + "\n" +
            " Veuillez patienter le temps qu'un de nos agents s'occupe de traitement \n de votre demande , essayer de ne pas renvoyer la même type de  demande.";
            Alerte alerte = new Alerte(user,existingDemande.getUtilisateur().getEmail(), msg_aa, "Repetition de demande", dateDemandeR);
            
            emailService.sendSimpleMail(alerte);
            
            alerteRepository.save(alerte);
            // throw new IllegalArgumentException("Une demande de  " + existingDemande.getTypeBanque().getNom() +" existe déjà pour l'utilisateur avec l'email " + existingDemande.getUtilisateur().getEmail());
            throw new IllegalArgumentException("Une demande de  " + existingDemande.getTypeBanque().getNom() +" est déjà  en cours de traitement pour vous Mr / Mme : " + existingDemande.getUtilisateur().getPrenom() + " "+ existingDemande.getUtilisateur().getNom());

        }
    
        demande.setNumeroDemande(numeroDemande);
    
        // Traitement du fichier image 1
        if (imageFile1 != null) {
            String imageLocation = "C:\\xampp\\htdocs\\solution_express";
            // String imageLocation = "C:\\xampp\\htdocs\\solution_express";
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
        demande.setAdmin(typeBanque.getBanque().getAdmin());
        demande.setStatutDemande("en cours");

        
        String dateDemande;
        LocalDate localDate3 = LocalDate.now();
        dateDemande = localDate3.toString();
        // Save the demande to the database
        Demande savedDemande = demandeRepository.save(demande);
        
        String msg_a = "Votre  demande de " + typeBanque.getNom() +
        " a été envoyée avec succès  à la banque "+ typeBanque.getBanque().getNom() + "\n" +
        " Veuillez patienter le temps qu'un de nos agents s'occupe de traitement \n de votre demande  Merci !!!..";
        Alerte alertes = new Alerte(user,savedDemande.getUtilisateur().getEmail(), msg_a, "Alerte reception de demande", dateDemande);
        // Alerte alertes = new Alerte(user,savedDemande.getUtilisateur().getEmail(), msg_a, "Alerte reception de demande", dateDemande);
        // Alerte alertes = new Alerte(user,demande.getUtilisateur().getEmail(), msg_a, "Alerte reception de demande", dateDemande);
        
        emailService.sendSimpleMail(alertes);
        // alerteRepository.save(alertes);
        

             // Envoi d'un e-mail à l'administrateur
       String message = "L'utilisateur  "+ savedDemande.getUtilisateur().getPrenom() + " " + savedDemande.getUtilisateur().getNom() +"a éffectué (e) une demande de " + savedDemande.getTypeBanque().getNom() +
                  " ses documents ont été reçu avec succès veuiller charger un agent de traiter sa demande dans les plus bref délai " +
                  " Mr  "+ savedDemande.getAdmin().getNom()+ " \n Merci !!!.";
                   
                  String date = LocalDate.now().toString();
      
                  Alerte alerte = new Alerte(savedDemande.getAdmin().getEmail(), message, "Nouvelle demande", date);
                  emailService.sendSimpleMail(alerte);
        
         
        
        // }
        
        
        
        // Alerte alertesa = new Alerte(savedDemande.getTypeBanque().getBanque().getAdmin().getEmail(), msg_ad, "Reception de demande", dateDemande);
            
            //  emailService.sendSimpleMail(alertesa);
    
            //  alerteRepository.save(alertesa);
    
         return demande;

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

    
    




    public ResponseEntity<List<Demande>> getAllDemande() {
        try {
            List<Demande> demandes = demandeRepository.findAll();
            return new ResponseEntity<>(demandes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

     public List<Demande> lireParUser(Integer idUtilisateur){
        List<Demande> demandes = demandeRepository.findByUtilisateurIdUtilisateur(idUtilisateur);
        if (demandes.isEmpty())
            throw new EntityNotFoundException("Aucune demande trouvée");
        return demandes;

    }

   

    public Demande validateDemande(Integer demandeId, Integer agentId)  {
        // Find the demande by its ID
        Demande demande = demandeRepository.findById(demandeId).orElse(null);
        if (demande == null) {
            throw new RuntimeException("Demande not found");
        }

         // Check if the demande is already validated
    if ("validé".equals(demande.getStatutDemande())) {
        throw new RuntimeException("Cette demande a déjà été validée.");
    }

    
        // Set the demande's status to "validé"
        demande.setStatutDemande("validé");
    
        // Find and associate agents with the demande
        Agent agent = agentRepository.findById(agentId).orElse(null);
        if (agent == null) {
            throw new RuntimeException("Agent non trouvé");
        }
        
        // Clear existing agents and add the new agent
        // demande.getAgent().clear();
        demande.getAgent().add(agent);
       
        // Save the updated demande
        Demande validatedDemande = demandeRepository.save(demande);
    
        // Send an email notification to the user
        String date = LocalDate.now().toString();
        String message = "Votre demande "+" " + validatedDemande.getTypeBanque().getNom() +" à la banque "+ validatedDemande.getTypeBanque().getBanque().getNom()+ " " +" a été validée avec succès.";
        Alerte alerte = new Alerte(validatedDemande.getUtilisateur().getEmail(), message, "Validation de demande", date);
        emailService.sendSimpleMail(alerte);
    
        return validatedDemande;
    }
    

    public Demande annulerDemande(Integer demandeId, Integer idUtilisateur)  {
        // Find the demande by its ID
        Demande demande = demandeRepository.findById(demandeId).orElse(null);
        if (demande == null) {
            throw new RuntimeException("Demande not found");
        }

         // Check if the demande is already validated
    if ("annulée".equals(demande.getStatutDemande())) {
        throw new RuntimeException("Cette demande a déjà été annuléé.");
    }

    
        // Set the demande's status to "validé"
        demande.setStatutDemande("annulée");
    
        // Find and associate agents with the demande
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElse(null);
        if (utilisateur == null) {
            throw new RuntimeException("Demande non trouvé");
        }
        
        // Clear existing agents and add the new agent
        // demande.getAgent().clear();
        
       
        // Save the updated demande
        Demande cancelDemande = demandeRepository.save(demande);
    
        // Send an email notification to the user
        String date = LocalDate.now().toString();
        String message = "Votre demande "+" " + cancelDemande.getTypeBanque().getNom() +" à la banque "+ cancelDemande.getTypeBanque().getBanque().getNom()+ " " +" a été annulée, si vous vouler faire une autre \n demande vous pouvez le faire à nouveau .";
        Alerte alerte = new Alerte(cancelDemande.getUtilisateur().getEmail(), message, "Annulation de demande", date);
        emailService.sendSimpleMail(alerte);


    
        return cancelDemande;
    }



    //Rejeter demande 
    public Demande rejectDemande(Integer demandeId, Integer agentId) {
        // Find the demande by its ID
        Demande demande = demandeRepository.findById(demandeId).orElse(null);
        if (demande == null) {
            throw new RuntimeException("Demande not found");
        }
         if ("rejeté".equals(demande.getStatutDemande())) {
        throw new RuntimeException("Cette demande a déjà été rejetée.");
    }
    
        // Set the demande's status to "rejeté"
        demande.setStatutDemande("rejeté");
    
        // Find and associate a new agent with the demande
        Agent agent = agentRepository.findById(agentId).orElse(null);
        if (agent == null) {
            throw new RuntimeException("Agent non trouvé");
        }
    
        // Create a new association between the demande and the agent
        demande.getAgent().add(agent);
    
        // Save the updated demande
        Demande rejectedDemande = demandeRepository.save(demande);
    
        // Send an email notification to the user with the rejection reason
        String date = LocalDate.now().toString();
        String message = "Votre demande de "+ " "+ rejectedDemande.getTypeBanque().getNom()+ " à la banque "+ " "+ rejectedDemande.getTypeBanque().getBanque().getNom()+ " a été rejetée parceque vos documents ne repondent pas \n aux exigences pour que la demande soit valider veuiller \n ressayer de réenvoyer une autre demande ou contacter notre assistance pour en savoir plus " ;
        Alerte alerte = new Alerte(rejectedDemande.getUtilisateur().getEmail(), message, "Rejet de demande", date);
        emailService.sendSimpleMail(alerte);
        alerteRepository.save(alerte);
    
        return rejectedDemande;
    }
    


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