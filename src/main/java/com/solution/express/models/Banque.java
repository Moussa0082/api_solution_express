package com.solution.express.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Banque {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idBanque;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String image;

    // //Liste des agents de la banque
    @OneToMany(mappedBy="banque" ,cascade = CascadeType.ALL)
    // @JsonIgnoreProperties(value = {"admin"})
    private  List<Agent> agents;
    
    // //Liste des types de demande de la banque
    @OneToMany
    (mappedBy="banque" ,cascade = CascadeType.ALL)
    // @JsonIgnoreProperties(value = {"admin"})
    private  List<TypeBanque> typeBanque;

    // //Liaison admin à banque
    @OneToOne
    @JoinColumn(name = "id_admin")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "idSuperAdmin")
    private SuperAdmin superAdmin;
    
}
