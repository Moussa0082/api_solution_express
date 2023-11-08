package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.Exceptions.NoContentException;
import com.solution.express.models.Banque;
import com.solution.express.models.Cotisation;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.CotisationRepository;
import com.solution.express.repository.UtilisateurRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CotisationService {

    @Autowired
    private CotisationRepository cotisationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepositoy;

    public Cotisation createCotisation(Cotisation cotisation, MultipartFile imageFile) throws Exception {
        if (cotisationRepository.findByNom(cotisation.getNom()) == null) {
            // Le reste de du code pour traiter l'image et enregistrer la cotisation
                  if (imageFile != null) {
                String imageLocation = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                // String imageLocation = "C:\\xampp\\htdocs\\solution_express";
                try {
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)) {
                        Files.createDirectories(imageRootLocation);
                    }
    
                    String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    cotisation.setImage("http://localhost/solution/" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
             LocalDate localDate = LocalDate.now();
             SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
             dateFormat.format(localDate);
             cotisation.setDateCreation(localDate.toString());
            return cotisationRepository.save(cotisation);
        } else {
            throw new IllegalArgumentException("La cotisation " + cotisation.getNom() + " existe déjà");
        }
    }

    //Modifier un cotisation
      public Cotisation updateCotisation(Integer id, Cotisation cotisation, MultipartFile imageFile) throws Exception {
        try {
            Cotisation cotisationExistant = cotisationRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Cotisation non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            cotisationExistant.setNom(cotisation.getNom());
            cotisationExistant.setFrais(cotisation.getFrais());
            cotisationExistant.setDescription(cotisation.getDescription());
            cotisationExistant.setDateCreation(cotisation.getDateCreation());
            cotisationExistant.setDateFin(cotisation.getDateFin());
            // utilisateurExistant.setMotDePasse(utilisateur.getMotDePasse());
            

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                // String emplacementImage = "C:\\xampp\\htdocs\\solution_express";
                String emplacementImage = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                // utilisateurExistant.setImage("http://localhost/solution_express\\images" + nomImage);
                cotisationExistant.setImage("http://localhost/solution/" + nomImage);
            }

            // Enregistrer le user mise à jour
            return cotisationRepository.save(cotisationExistant);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de la cotisation avec l'ID : " + id);

        } 
    }


    public void addUserToCotisation(int cotisationId, int userId) throws Exception {
        Cotisation cotisation = cotisationRepository.findById(cotisationId).orElseThrow(() -> new Exception("Cotisation introuvable"));
        Utilisateur utilisateur = utilisateurRepositoy.findById(userId).orElseThrow(() -> new Exception("Utilisateur introuvable"));

        if (cotisation.getUtilisateur().contains(utilisateur)) {
            throw new Exception("L'utilisateur est déjà membre de la cotisation");
        }

        cotisation.getUtilisateur().add(utilisateur);
        cotisationRepository.save(cotisation);
    }



    //recuperer la liste des ueser
        public List<Cotisation> getAllCotisation(){

        List<Cotisation> cotisation = cotisationRepository.findAll();
        if (cotisation.isEmpty())
            throw new NoContentException("Aucune cotisation trouvé");
        return cotisation;
    }

    //Recuperer la cotisation par utilisateur
    public List<Cotisation> getAllCotisationByUtilisateur(Integer idUtilisateur){
        List<Cotisation>  cotisation = cotisationRepository.findByUtilisateurIdUtilisateur(idUtilisateur);

        if(cotisation.isEmpty()){
            throw new EntityNotFoundException("Aucune cotisation trouvé");
        }

        return cotisation;
    }

    



    //Get user byID
    public ResponseEntity<?> findById(Integer id) {

    Optional<Cotisation> cotisation = cotisationRepository.findById(id);

    if (cotisation.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La cotisation avec l'ID " + id + " n'existe pas");
    }

    return ResponseEntity.ok(cotisation.get());     
   }
   

        

    //suppression d'une cotisation specifique 
    public String deleteCotisation(Integer id) {
        Optional <Cotisation> cotisation = cotisationRepository.findById(id);
         if (cotisation.isPresent()) {
             cotisationRepository.deleteById(id);
             return "Cotisation supprimé avec succès.";
         } else {
           
             return "Cotisation non existant.";
         }
     }


   
     
    
}
