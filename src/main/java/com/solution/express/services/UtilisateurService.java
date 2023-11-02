package com.solution.express.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solution.express.Exceptions.NoContentException;
import com.solution.express.models.Utilisateur;
import com.solution.express.repository.UtilisateurRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;



    //recuperer la liste des ueser
        public List<Utilisateur> getAllUtilisateur(){

        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        if (utilisateurs.isEmpty())
            throw new NoContentException("Aucun utilisateur trouvé");
        return utilisateurs;
    }
    public Utilisateur getUtilisateurById(int idUtilisateur){

        Utilisateur utilisateur= utilisateurRepository.findByIdUtilisateur(idUtilisateur);
        if(utilisateur ==null)
            throw new EntityNotFoundException("cet utilisateur n'existe pas");
        return utilisateur;
    }

    //suppression d'un user specifique 
    public String deleteUtilisateurById(Utilisateur utilisateur){
        Utilisateur user= utilisateurRepository.findByIdUtilisateur(utilisateur.getIdUtilisateur());
        if (user == null)
            throw new EntityNotFoundException("Désolé  l'utilisateur n'existe pas");
        utilisateurRepository.delete(utilisateur);
        return "Utilisateur Supprimé";
    }

    //Methode se connecter
    // public Utilisateur connectionUtilisateur(String email, String motDePasse) {
    //     // Utilisateur utilisateur;
    //     // Utilisateur user = utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);
    //     // if (user == null) {
    //     //     throw new EntityNotFoundException("Cet utilisateur n'existe pas");
    //     // }
    //     // // if(utilisateur!= null){
    //     // //    String nom = utilisateur.getEmail();
    //     // //    String mail = utilisateur.getEmail();
    //     // // }

    //     // return user;
    // }
    
}
