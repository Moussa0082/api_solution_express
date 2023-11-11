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

        // public Evenement createEvenement(Evenement evenement) throws Exception {
        //     Cotisation co = cotisationRepository.findByCreateurAndIdCotisation(evenement.getCotisation().getCreateur(), evenement.getCotisation().getIdCotisation());
        //     // if( co == null ){

        //     //   evenementRepository.save(evenement);
        //     // }
        //     if (evenement.getCotisation() != null && evenement.getCotisation().getCreateur() != null) {
        //         Cotisation cos = cotisationRepository.findByCreateurAndIdCotisation(
        //             evenement.getCotisation().getCreateur(),
        //             evenement.getCotisation().getIdCotisation()
        //         );
        
        //         if (cos == null) {
        //             evenementRepository.save(evenement);
        //         }
        //     }
        
        //     return evenement;
            
        // }
 
        // public Evenement createEvenement(Evenement evenement, Cotisation cotisation) throws Exception {
        //     // Récupérer la Cotisation associée à l'Evenement
        //     // Cotisation cotisations = evenement.getCotisation();
            
        //     // Vérifier si la Cotisation existe déjà dans la base de données
        //     Cotisation existingCotisation = cotisationRepository.findById(evenement.getCotisation().getIdCotisation()).orElse(null);
        
        //     if (existingCotisation == null) {
        //         // Si la Cotisation n'existe pas, enregistrer la Cotisation et l'Evenement
        //         // cotisationRepository.save(cotisation);
        //         // evenementRepository.save(evenement);
        //         throw new BadRequestException("La Cotisation avec l'ID " + cotisation.getIdCotisation() + " n'existe pas.");

        //     } else {
        //         // Si la Cotisation existe, associer l'Evenement à cette Cotisation existante
        //         evenement.setCotisation(existingCotisation);
        //         evenementRepository.save(evenement);
        //     }
        
        //     return evenement;
        // }

        public Evenement createEvenementWithCotisation(Evenement evenement, Integer idCotisation) {
            // Assurez-vous que la cotisation existe en vérifiant son ID
            Cotisation cotisation = cotisationRepository.findById(idCotisation)
                    .orElseThrow(() -> new IllegalArgumentException("Cotisation non trouvée avec l'ID : " + idCotisation));
        
            // Associez la cotisation à l'événement
            evenement.setCotisation(cotisation);
            // Insérez l'événement dans la base de données
            Evenement savedEvenement = evenementRepository.save(evenement);
            

             List<Utilisateur> membresCotisation = cotisation.getUtilisateur();
    
    for (Utilisateur utilisateur : membresCotisation) {
        String dateEvenement = savedEvenement.getDateEvenement();
        String msg = "Nouvel événement : " + savedEvenement.getNomEvenement() +
            " le " + dateEvenement + "votre présence est vivement souhaitée";
        
        Alerte alerte = new Alerte(utilisateur, utilisateur.getEmail(), msg, "Nouvel événement", dateEvenement);
        
        // Envoyez un e-mail à chaque membre de la cotisation
        emailService.sendSimpleMail(alerte);
    }
        
            return evenement;
        }
        
        
       

    //Modifier evenement methode
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


        //Recuperer la cotisation par utilisateur
        // public List<Evenement> getAllEvenemenetByUtilisateur(Integer idUtilisateur){
        // List<Evenement>  evenement = evenementRepository.findByUtilisateurIdUtilisateur(idUtilisateur);

        // if(evenement.isEmpty()){
        //     throw new EntityNotFoundException("Aucun evenement trouvé");
        // }

        // return evenement;
        // }


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
