package com.solution.express.models;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alerte {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlerte;

    @Email
    private String email;

    
    
    private String mesage;

    private String sujet;

  
    private String date;

  //lier l'utilisateur à l'alerte pour voir l'utilisateur concerner par l'alerte en question
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Admin admin;

    public Alerte(Utilisateur utilisateur,String email, String mesage, String sujet, String date){
      this.utilisateur = utilisateur;
      this.email = email;
      this.mesage = mesage;
      this.sujet = sujet;
      this.date = date;
    }

    public Alerte(Utilisateur utilisateur,String email, String mesage, String sujet, String date,Admin admin){
      this.email = email;
      this.mesage = mesage;
      this.sujet = sujet;
      this.date = date;
    }
    public Alerte(String email, String mesage, String sujet, String date){
      this.email = email;
      this.mesage = mesage;
      this.sujet = sujet;
      this.date = date;
    }
    public Alerte(String email, String mesage, String sujet){
      this.email = email;
      this.mesage = mesage;
      this.sujet = sujet;
    }
    public Alerte( Admin admin, String email, String mesage, String sujet, String date){
      this.admin = admin;
      this.email = email;
      this.mesage = mesage;
      this.sujet = sujet;
      this.date = date;
    }

    public Alerte( Utilisateur utilisateur,Admin admin, String email, String mesage, String sujet, String date){
      this.admin = admin;
      this.email = email;
      this.mesage = mesage;
      this.sujet = sujet;
      this.date = date;
    }
   
    
}
