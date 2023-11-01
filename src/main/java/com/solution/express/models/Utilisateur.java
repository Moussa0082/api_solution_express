package com.solution.express.models;


import java.util.List;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Utilisateur {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idUtilisateur;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false, unique = true)
    // @Email(message = "Email incorrect !")
    private String email;

    @Column(nullable = false)
    private String motDePasse;

        
   //Liaison demande à l'utilisateur pour voir la liste des demandes effectuer par le user
    @OneToMany(mappedBy="utilisateur")
    // @JsonIgnoreProperties(value = {"utiliateur"})
    private  List<Demande> demande;
    
    //Liaison le paiement à l'utilisateur pour voir la liste des paiements effectuer par le user
     @OneToMany(mappedBy="utilisateur")
     // @JsonIgnoreProperties(value = {"utiliateur"})
     private  List<Paiement> paiement;

    //Liaison cotisation aux user
    @ManyToMany(mappedBy="utilisateur")
    private List<Cotisation> cotisation;
  
    //Pour afficher la liste des raports elaborer par un user specifique
    @OneToMany(mappedBy="utilisateur")
    private List<Rapport> rapport;
    
    //Pour afficher la liste des evenements planifier par un user specifique
    @OneToMany(mappedBy="utilisateur")
    private List<Evenement> evenement;

    
}
