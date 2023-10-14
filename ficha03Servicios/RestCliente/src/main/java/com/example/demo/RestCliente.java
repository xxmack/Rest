package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestCliente {
    private static final String GET_ALL_ESTUDIANTES_API = "http://localhost:8080/api/estudiantes";
    private static final String GET_ESTUDIANTES_BY_ID_API = "http://localhost:8080/api/estudiantes/{id}";

    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        callGetAllEstudiantesAPI();
        
        System.out.println("Clientes: ");
        callGetEstudianteByIdAPI(1);
        callGetEstudianteByIdAPI(2);
        callGetEstudianteByIdAPI(3);
    }

    private static void callGetEstudianteByIdAPI(int numberId) {
        Map<String, Integer> parametros = new HashMap<>();
        parametros.put("id", numberId);

        String resultado = restTemplate.getForObject(GET_ESTUDIANTES_BY_ID_API, String.class, parametros);
        System.out.println(resultado);
    }

    private static void callGetAllEstudiantesAPI() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(GET_ALL_ESTUDIANTES_API, HttpMethod.GET, entity, String.class);
        System.out.println(result);
    }
}

