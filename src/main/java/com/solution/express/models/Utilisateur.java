package com.solution.express.models;


import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty
    @Email
    @Size(max = 255)
    @Column(unique = true)
    // @Email(message = "Email incorrect !")
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Column(unique = true)
    private String image;

     //pour recuperr la liste des alertes d'un utlisateur specifique
    @OneToMany
    (mappedBy = "utilisateur",cascade = CascadeType.ALL)
     private List<Alerte> alerte;

        
   //Liaison demande à l'utilisateur pour voir la liste des demandes effectuer par le user
    @OneToMany
    (mappedBy="utilisateur", cascade =  CascadeType.ALL)
    // @JsonIgnoreProperties(value = {"utiliateur"})
    private  List<Demande> demande;
    
    // //Liaison le paiement à l'utilisateur pour voir la liste des paiements effectuer par le user
     @OneToMany
     (mappedBy="utilisateur", cascade =  CascadeType.ALL)
     // @JsonIgnoreProperties(value = {"utiliateur"})
     private  List<Paiement> paiement;

    // //Liaison cotisation aux user
    @ManyToMany
    (mappedBy="utilisateur")
    private List<Cotisation> cotisation;
  
    //Pour afficher la liste des raports elaborer par un user specifique
    @OneToMany
    (mappedBy="utilisateur", cascade = CascadeType.ALL)
    private List<Rapport> rapport;
    
    // //Pour afficher la liste des evenements planifier par un user specifique
    @OneToMany
     (mappedBy="utilisateur", cascade = CascadeType.ALL)
    private List<Evenement> evenement;

    
}
