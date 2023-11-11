package com.solution.express.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.Exceptions.BadRequestException;
import com.solution.express.models.Alerte;
import com.solution.express.models.Banque;
import com.solution.express.models.Cotisation;
import com.solution.express.models.Evenement;
import com.solution.express.models.SuperAdmin;
import com.solution.express.models.TypeBanque;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.CotisationRepository;
import com.solution.express.repository.EvenementRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EvenementService {
 
    @Autowired
    private EvenementRepository evenementRepository;

  @Autowired
  private CotisationRepository cotisationRepository;

  @Autowired
  private EmailService emailService;

      

        public Evenement createEvenementWithCotisation(Evenement evenement, Integer idCotisation) {
            // Assurez-vous que la cotisation existe en vérifiant son ID
            Cotisation cotisation = cotisationRepository.findById(idCotisation)
                    .orElseThrow(() -> new IllegalArgumentException("Cotisation non trouvée avec l'ID : " + idCotisation));
            Evenement  ev = evenementRepository.findByDateEvenement(evenement.getDateEvenement());
            if ( ev != null) {
                throw new BadRequestException("L'événement " + evenement.getNomEvenement() + " existe déjà le " + evenement.getDateEvenement());
            }
            // Associez la cotisation à l'événement
            evenement.setCotisation(cotisation);
            // Insérez l'événement dans la base de données
            Evenement savedEvenement = evenementRepository.save(evenement);
            

             List<Utilisateur> membresCotisation = cotisation.getUtilisateur();
    
    for (Utilisateur utilisateur : membresCotisation) {
        String dateEvenement = savedEvenement.getDateEvenement();
        String msg = "Nouvel événement : " + " " + savedEvenement.getNomEvenement() +
            " le " + dateEvenement + "  votre présence est vivement souhaitée";
        
        Alerte alerte = new Alerte(utilisateur, utilisateur.getEmail(), msg, "Nouvel événement" + " du groupe " + savedEvenement.getCotisation().getNom() , dateEvenement);
        
        // Envoyez un e-mail à chaque membre de la cotisation
        emailService.sendSimpleMail(alerte);
    }
        
            return evenement;
        }
        
        
       

    //Modifier evenement Methode
    public Evenement updateEvenement(Integer id, Evenement evenement) {
        return evenementRepository.findById(id)
                .map(ev -> {
                    ev.setNomEvenement(evenement.getNomEvenement());
                    ev.setDescriptionEvenement(evenement.getDescriptionEvenement());
                    ev.setLieuEvenement(evenement.getLieuEvenement());
                    ev.setDateEvenement(evenement.getDateEvenement());
                    ev.setHeureEvenement(evenement.getHeureEvenement());
                    return evenementRepository.save(ev);
                }).orElseThrow(() -> new RuntimeException(("Evenement non existant avec l'ID " + id)));
    
    }


        //Recuperer les evenement par ID groupe de cotisation
        public List<Evenement> getAllEvenemenetByCotisation(Integer idCotisation){
        List<Evenement>  evenement = evenementRepository.findByCotisationIdCotisation(idCotisation);

        if(evenement.isEmpty()){
            throw new EntityNotFoundException("Aucun evenement trouvé");
        }

        return evenement;
        }


    //Recuperer la liste des evenement
      public ResponseEntity<List<Evenement>> getAllEvenement() {
     
       try {
            return new ResponseEntity<>(evenementRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
             e.printStackTrace();
        }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> findById(Integer id) {

        Optional<Evenement> evenement = evenementRepository.findById(id);
    
        if (evenement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evenement avec l'ID " + id + " n'existe pas");
        }
    
        return ResponseEntity.ok(evenement.get());     
       }

//       //suppression d'un super admin specifique 
    public String deleteEvenement(Integer id) {
        Optional <Evenement> evenement = evenementRepository.findById(id);
         if (evenement.isPresent()) {
             evenementRepository.deleteById(id);
             return "Evenement supprimé avec succès.";
         } else {
           
             return "Evenement non existant.";
         }
     }



}
