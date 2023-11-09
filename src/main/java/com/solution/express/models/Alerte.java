package com.solution.express.models;

import java.util.List;

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

    @NotEmpty
    @Email
    private String email;

    
    // @Size(max = 255)
    // @Column(unique = true)
    private String mesage;

    private String sujet;

    // @Temporal(TemporalType.DATE)
    // @JsonFormat(pattern = "dd-MM-yyyy")
    private String date;

  //lier l'utilisateur à l'alerte pour voir l'utilisateur concerner par l'alerte en question
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur utilisateur;

    public Alerte(Utilisateur utilisateur,String email, String mesage, String sujet, String date){
      this.utilisateur = utilisateur;
      this.email = email;
      this.mesage = mesage;
      this.sujet = sujet;
      this.date = date;
    }
    
}
