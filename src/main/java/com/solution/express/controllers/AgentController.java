package com.solution.express.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.solution.express.models.Agent;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.AgentRepository;

import com.solution.express.services.AgentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/agent")
public class AgentController {
 
   
  @Autowired
  private  AgentService  agentService;

  @Autowired
  private AgentRepository agentRepository;

  //Create user
          @PostMapping("/create")
    @Operation(summary = "Création d'un agent")
    public ResponseEntity<Agent> createAgent(
            @Valid @RequestParam("agent") String agentString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile)
            throws Exception {

        Agent agent = new Agent();
        try {
            agent = new JsonMapper().readValue(agentString, Agent.class);
        } catch (JsonProcessingException e) {
            throw new Exception(e.getMessage());
        }

        Agent savedAgent = agentService.createAgent(agent, imageFile);

        return new ResponseEntity<>(savedAgent, HttpStatus.CREATED);
    }

     
      //Modifier agent
    @PutMapping("/update/{id}")
    @Operation(summary = "Mise à jour d'un agent par son Id ")
    public ResponseEntity<Agent> updateAgent(
            @PathVariable Integer id,
            @Valid @RequestParam("agent") String agentString,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        Agent agent = new Agent();
        try {
            agent = new JsonMapper().readValue(agentString, Agent.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Agent agentMisAjour = agentService.updateAgent(id, agent, imageFile);
            return new ResponseEntity<>(agentMisAjour, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     
    //Liste des agents
    @GetMapping("/read")
     @Operation(summary = "Affichage de la  liste des agents")
    public ResponseEntity<List<Agent>> getAllAgent() {
        return agentService.getAllAgent();
    }


       
    //Lire un agent spécifique
    @GetMapping("/read/{id}")
    public ResponseEntity<?> getAgentById(@PathVariable Integer id) {
        return agentService.findById(id);
    }


        //Supprimer un agent
           @DeleteMapping("/delete/{id}")
           public String deleteAgent(@PathVariable Integer id){
             Optional <Agent> agent = agentRepository.findById(id);
             if (agent.isPresent()) {
                 agentRepository.deleteById(id);
                 agentService.deleteAgent(id);  
                 return " Agent supprimé avec succès.";
             } else {
               
                 return "Agent non existant avec l'ID " + id;
             }
           }

           @PatchMapping("/disable/{id}")
           //Desactiver un admin methode
           public ResponseEntity <String> disableAgent(@PathVariable Integer id){
           
               agentService.disableAgent(id);
               return new ResponseEntity<>("Agent desactiver avec succes", HttpStatus.ACCEPTED);
           }
       
           //Aciver admin
             @PatchMapping("/enable/{id}")
           //Desactiver un admin methode
           public ResponseEntity <String> enableAgent(@PathVariable Integer id){
           
               agentService.enableAgent(id);
               return new ResponseEntity<>("Agent activer avec succes", HttpStatus.ACCEPTED);
           }

    }



     

