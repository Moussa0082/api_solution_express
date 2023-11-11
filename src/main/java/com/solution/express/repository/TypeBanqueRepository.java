package com.solution.express.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solution.express.models.Banque;
import com.solution.express.models.Rapport;
import com.solution.express.models.TypeBanque;

public interface TypeBanqueRepository  extends JpaRepository<TypeBanque,Integer>{

    TypeBanque findTypeBanqueByNom (String nom);

    TypeBanque findBanqueByNom (Banque banque);

    Optional<TypeBanque> findByNomAndBanque(String nom, Banque idBanque);

    public List<TypeBanque> findByBanqueIdBanque(Integer idBanque);


}
