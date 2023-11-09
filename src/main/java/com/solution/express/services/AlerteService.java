package com.solution.express.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solution.express.Exceptions.BadRequestException;
import com.solution.express.models.Alerte;
import com.solution.express.repository.AlerteRepository;

@Service
public class AlerteService {

    @Autowired
    private AlerteRepository alerteRepository;


    //   public List<Alerte> getAlerteByUtilisateur(Integer idUtilisateur){
    //     List<Alerte>  alerte = alerteRepository.findByUtilisateur(idUtilisateur);

    //     if(alerte.isEmpty()){
    //         throw new BadRequestException("Aucun evenement trouvé");
    //     }

    //     return alerte;
    //     }
    
}
