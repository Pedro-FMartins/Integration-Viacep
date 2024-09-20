package com.example.crud.service;

import com.example.crud.domain.address.Address;
import com.example.crud.domain.product.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class SearchCEP {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public SearchCEP(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Boolean searchCEP(String cep, Product prod){
        String url = "https://viacep.com.br/ws/{cep}/json/";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, cep);

        try{
            Address address= objectMapper.readValue(response.getBody(), Address.class);

            if(prod.getDistribution_center().equals(address.getLocalidade())){
                return Boolean.TRUE;
            }
            else{
                return Boolean.FALSE;
            }
        }catch(JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }
}
