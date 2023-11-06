package com.solution.express.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.solution.express.models.Admin;
import com.solution.express.models.Agent;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AgentRepository;

@Service
public class AgentService {

  @Autowired
  private AgentRepository agentRepository;



    //creer un agent
      public Agent createAgent(Agent agent, MultipartFile imageFile) throws Exception {
        if (agentRepository.findByEmail(agent.getEmail()) == null) {
    
            // Traitement du fichier image
            if (imageFile != null) {
                String imageLocation = "C:\\xampp\\htdocs\\solution_express";
                try {
                    Path imageRootLocation = Paths.get(imageLocation);
                    if (!Files.exists(imageRootLocation)) {
                        Files.createDirectories(imageRootLocation);
                    }
    
                    String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                    Path imagePath = imageRootLocation.resolve(imageName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    agent.setImage("http://localhost/solution_express\\images" + imageName);
                } catch (IOException e) {
                    throw new Exception("Erreur lors du traitement du fichier image : " + e.getMessage());
                }
            }
    
    
            return agentRepository.save(agent);
        } else {
            throw new IllegalArgumentException("L'agent avec l'amail " + agent.getEmail() + " existe déjà");
        }
    }

            //verifier si l'email existe deja
            public boolean emailExisteDeja(String email) {
                return agentRepository.existsByEmail(email);
            }

            //Modifier  agent methode
            
     public Agent updateAgent(Integer id, Agent agent, MultipartFile imageFile) throws Exception {
        try {
            Agent agentExistant = agentRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Agent non trouvée avec l'ID : " + id));

            // Mettre à jour les champs
            agentExistant.setNom(agent.getNom());
            agentExistant.setPrenom(agent.getPrenom());
            agentExistant.setEmail(agent.getEmail());
            agentExistant.setMotDePasse(agent.getMotDePasse());
            

            // Mettre à jour l'image si fournie
            if (imageFile != null) {
                // String emplacementImage = "C:\\xampp\\htdocs\\solution_express";
                String emplacementImage = "C:\\Users\\bane.moussa\\Documents\\api_solution_express";
                String nomImage = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path cheminImage = Paths.get(emplacementImage).resolve(nomImage);

                Files.copy(imageFile.getInputStream(), cheminImage, StandardCopyOption.REPLACE_EXISTING);
                // adminExistant.setImage("http://localhost/solution_express\\images" + nomImage);
                agent.setImage("http://localhost/solution/" + nomImage);
            }

            // Enregistrer le user mise à jour
            return agentRepository.save(agentExistant);
        } catch (NoSuchElementException ex) {
           throw new NoSuchElementException("Une erreur s'est produite lors de la mise à jour de l'agent avec l'ID : " + id);

        } 
    }

         //Recuperer la liste des agents
            public ResponseEntity<List<Agent>> getAllAgent() {
            
            try {
                    return new ResponseEntity<>(agentRepository.findAll(), HttpStatus.OK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
           
            //supprimer agent
        public String deleteSuperAgent(Integer id) {
        Optional <Agent> agent = agentRepository.findById(id);
         if (agent.isPresent()) {
             agentRepository.deleteById(id);
             return "Agent supprimé avec succès.";
         } else {
           
             return "Agent non existant.";
         }
     }

     //Get agent byID
    public ResponseEntity<?> findById(Integer id) {

        Optional<Agent> agent = agentRepository.findById(id);
    
        if (agent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'agent avec l'ID " + id + " n'existe pas");
        }
    
        return ResponseEntity.ok(agent.get());     
       }


    //Desactiver un agent
    public ResponseEntity<String> disableAgent(Integer id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agent.get().setIsActive(false);
            agentRepository.save(agent.get());
            return new ResponseEntity<>("L'agent " + agent.get().getPrenom() + " " + agent.get().getNom() + " a été désactivé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Agent non trouvé avec l'ID " + id, HttpStatus.BAD_REQUEST);
        }
    }
   
    //activer agent
    public ResponseEntity<String> enableAgent(Integer id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agent.get().setIsActive(true);
            agentRepository.save(agent.get());
            return new ResponseEntity<>("L'agent " + agent.get().getPrenom() + " " + agent.get().getNom() + " a été activé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Agent non trouvé avec l'ID " + id, HttpStatus.BAD_REQUEST);
        }
    }
    
    //Supprimer agent
     public String deleteAgent(Integer id) {
        Optional <Agent> agent = agentRepository.findById(id);
         if (agent.isPresent()) {
             agentRepository.deleteById(id);
             return "Agent supprimé avec succès.";
         } else {
           
             return "Agent non existant.";
         }
     }


     //Desactiver un agent

    public ResponseEntity<String> disableAdmin(Integer id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agent.get().setIsActive(false);
            agentRepository.save(agent.get());
            return new ResponseEntity<>("L'agent " + agent.get().getPrenom() + " " + agent.get().getNom() + " a été désactivé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Agent non trouvé avec l'ID " + id, HttpStatus.BAD_REQUEST);
        }
    }
   
}




