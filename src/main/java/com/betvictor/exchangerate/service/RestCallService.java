package com.betvictor.exchangerate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RestCallService {
    @Autowired
    private RestTemplate restTemplate;

    public String convertCurrency() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange("https://api.exchangerate.host/convert?from=USD&to=RON&amount=1&places=2", HttpMethod.GET, entity, String.class).getBody();
    }
}
