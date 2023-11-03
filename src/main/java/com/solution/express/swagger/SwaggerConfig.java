package com.solution.express.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {
    
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API DE GESTION DE DEMANDE DE CREATION DE COMPTE BANCAIRE ET D'OBTENTION DE CARTE PREPAYEE , \nDE GESTION DE COTISATION D'UN GROUPE DE PERSONNE (AMIE, FAMILLE, CITE , AIDE FINANCIERE) ")
                        .description("Cette api est fait specialement pour la gestion complete des demandes de création de compte \n , d'obtention de carte prépayée , et l'api gère aussi \n les cotisations pour les groupes de personnes \n comme par exemple un goupe d'amie, de famille , de cité , des groupes d'association pour la recolte de fond \n et autre ...")
                        .version("1.0"));
    }
    
}
