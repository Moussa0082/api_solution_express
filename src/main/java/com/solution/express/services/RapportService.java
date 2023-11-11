package com.solution.express.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solution.express.Exceptions.BadRequestException;
import com.solution.express.models.Alerte;
import com.solution.express.models.Cotisation;
import com.solution.express.models.Rapport;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.CotisationRepository;
import com.solution.express.repository.RapportRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RapportService {

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private CotisationRepository cotisationRepository;

    @Autowired
    private EmailService emailService;


      public Rapport createRapportWithCotisation(Rapport rapport, Integer idCotisation) {
            // Assurez-vous que la cotisation existe en vérifiant son ID
            Cotisation cotisation = cotisationRepository.findById(idCotisation)
                    .orElseThrow(() -> new IllegalArgumentException("Cotisation non trouvée avec l'ID : " + idCotisation));
            Rapport  rp = rapportRepository.findByMessage(rapport.getMessage());
            if ( rp != null) {
                throw new BadRequestException("Le rapport avec le message " + rapport.getMessage() + " existe déjà , essayer d'ajouter un autre message\n pour le rapport que vous voulez poster  ");
            }
            // Associez la cotisation à l'événement
            rapport.setCotisation(cotisation);
            // Insérez l'événement dans la base de données
            Rapport savedRapport = rapportRepository.save(rapport);
            

             List<Utilisateur> membresCotisation = cotisation.getUtilisateur();
    
    for (Utilisateur utilisateur : membresCotisation) {
        String dateRapport = savedRapport.getDateRapport().toString();
        String msg = "Nouvel rapport  : " + " " + savedRapport.getMessage() +
            " le " + dateRapport + " à " + savedRapport.getHeureRapport().toString();
        
        Alerte alerte = new Alerte(utilisateur, utilisateur.getEmail(), msg, "Nouvel rapport poster" + " sur le groupe " + savedRapport.getCotisation().getNom() , dateRapport);
        
        // Envoyez un e-mail à chaque membre de la cotisation
        emailService.sendSimpleMail(alerte);
    }
        
            return rapport;
        }
        
        
       

    //Modifier rapport Methode
    // public Rapport updateRapport(Integer id, Rapport rapport) {
    //     return rapportRepository.findById(id)
    //             .map(rp -> {
    //                 rp.setDateRapport(rapport.getDateRapport());
    //                 rp.setHeureRapport(rapport.getHeureRapport());
    //                 rp.setLieu(rapport.getLieu());
    //                 rp.setMessage(rapport.getMessage());
    //                 return rapportRepository.save(rp);
    //             }).orElseThrow(() -> new RuntimeException(("Rapport non existant avec l'ID " + id)));
    
    // }

    public Rapport updateRapport(Integer id, Rapport updatedRapport) {
        Rapport existingRapport = rapportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rapport non trouvé avec l'ID : " + id));
    
        // Comparez les champs pour détecter les modifications
        if (!existingRapport.getMessage().equals(updatedRapport.getMessage())) {
            // Le champ "message" a été modifié
            // Envoyez un e-mail à tous les membres de la cotisation avec le rapport modifié
            List<Utilisateur> membresCotisation = existingRapport.getCotisation().getUtilisateur();
            String dateRapport = existingRapport.getDateRapport().toString();
            String msg = "Le rapport a été modifié : " + updatedRapport.getMessage() +
                " la date c'est le  " + dateRapport + " à " + updatedRapport.getHeureRapport().toString();
    
            for (Utilisateur utilisateur : membresCotisation) {
                Alerte alerte = new Alerte(utilisateur, utilisateur.getEmail(), msg, "Modification de rapport" + " sur le groupe " + existingRapport.getCotisation().getNom(), dateRapport);
                
                // Envoyez un e-mail à chaque membre de la cotisation
                emailService.sendSimpleMail(alerte);
            }
        }
    
        // Mettez à jour le rapport dans la base de données
        existingRapport.setDateRapport(updatedRapport.getDateRapport());
        existingRapport.setHeureRapport(updatedRapport.getHeureRapport());
        existingRapport.setLieu(updatedRapport.getLieu());
        existingRapport.setMessage(updatedRapport.getMessage());
        
        Rapport updatedRapports = rapportRepository.save(existingRapport);
        
        return updatedRapports;
    }
    


        //Recuperer les rapport par ID groupe de cotisation
        public List<Rapport> getAllRapportByCotisation(Integer idCotisation){
        List<Rapport>  rapport = rapportRepository.findByCotisationIdCotisation(idCotisation);

        if(rapport.isEmpty()){
            throw new EntityNotFoundException("Aucun rapport trouvé");
        }

        return rapport;
        }


    //Recuperer la liste des raports
      public ResponseEntity<List<Rapport>> getAllRapport() {
     
       try {
            return new ResponseEntity<>(rapportRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
             e.printStackTrace();
        }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    //Get rapport by ID
    public ResponseEntity<?> findById(Integer id) {

        Optional<Rapport> rapport = rapportRepository.findById(id);
    
        if (rapport.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evenement avec l'ID " + id + " n'existe pas");
        }
    
        return ResponseEntity.ok(rapport.get());     
       }

      //suppression d'un rapport  specifique 
    public String deleteRapport(Integer id) {
        Optional <Rapport> rapport = rapportRepository.findById(id);
         if (rapport.isPresent()) {
             rapportRepository.deleteById(id);
             return "Rapport supprimé avec succès.";
         } else {
           
             return "Rapport non existant.";
         }
     }


    
    
}
