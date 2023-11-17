package com.solution.express.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solution.express.models.Cotisation;
import com.solution.express.models.Evenement;
import com.solution.express.models.SuperAdmin;
import com.solution.express.repository.EvenementRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EvenementService {
 
    @Autowired
    private EvenementRepository evenementRepository;

    //Methode pour créer un super admin 
     public ResponseEntity<String> createEvenement(Evenement evenement) {
         if (evenementRepository.findByNomEvenementAndDateEvenement(evenement.getNomEvenement(), evenement.getDateEvenement())) {
             evenementRepository.save(evenement);

             return new ResponseEntity<>("Evenement ajouter avec succès", HttpStatus.CREATED);
            } else {
                
                return new ResponseEntity<>("L'evenement "+ evenement.getNomEvenement() + "ne peut pas etre ajouter" + "2 fois à la meme date le" + evenement.getDateEvenement() , HttpStatus.BAD_REQUEST);
         }
        }
        
       
    // }

    //Modifier evenement methode
    public Evenement updateEvenement(Integer id, Evenement evenement) {
        return evenementRepository.findById(id)
                .map(ev -> {
                    ev.setNomEvenement(evenement.getNomEvenement());
                    ev.setDescriptionEvenment(evenement.getDescriptionEvenment());
                    ev.setLieuEvenement(evenement.getLieuEvenement());
                    ev.setDateEvenement(evenement.getDateEvenement());
                    ev.setHeureEvenement(evenement.getHeureEvenement());
                    return evenementRepository.save(ev);
                }).orElseThrow(() -> new RuntimeException(("Evenement non existant avec l'ID " + id)));
    
    }


      //Recuperer la cotisation par utilisateur
    // public List<Evenement> getAllEvenemenetByUtilisateur(Integer idUtilisateur){
    //     List<Evenement>  evenement = evenementRepository.findEvenementByUtilisateurAndEvenement(idUtilisateur, evenement.getNom);

    //     if(cotisation.isEmpty()){
    //         throw new EntityNotFoundException("Aucune cotisation trouvé");
    //     }

    //     return cotisation;
    // }


    //Recuperer la liste des superAdmins
      public ResponseEntity<List<Evenement>> getAllEvenement() {
     
       try {
            return new ResponseEntity<>(evenementRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
             e.printStackTrace();
        }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

//       //suppression d'un super admin specifique 
    public String deleteSuperAdmin(Integer id) {
        Optional <Evenement> evenement = evenementRepository.findById(id);
         if (evenement.isPresent()) {
             evenementRepository.deleteById(id);
             return "Evenement supprimé avec succès.";
         } else {
           
             return "Evenement non existant.";
         }
     }



}
