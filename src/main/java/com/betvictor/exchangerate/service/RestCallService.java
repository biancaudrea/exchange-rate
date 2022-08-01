package com.betvictor.exchangerate.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class RestCallService {
    @Autowired
    private RestTemplate restTemplate;

    public String convertCurrencyAToB(String from, String to) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://api.exchangerate.host/convert?amount=1&places=2&from=" + from + "&to=" + to;
        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        JSONObject object = new JSONObject(json);
        return object.getString("result");
    }

    public String convertCurrencyToAllExchangeRates(String currency) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://api.exchangerate.host/latest?amount=1&places=2&base=" + currency;
        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        JSONObject object = new JSONObject(json);
        return object.getString("rates");
    }

    public String getValueConversion(String from, String to, Integer amount) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://api.exchangerate.host/convert?places=2&from=" + from + "&to=" + to + "&amount=" + amount;
        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        JSONObject object = new JSONObject(json);
        return object.getString("result");
    }

    public String getAllValueConversions(String currency, Integer amount, String symbols) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://api.exchangerate.host/latest?places=2&base=" + currency + "&amount=" + amount + "&symbols=" + symbols;
        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        JSONObject object = new JSONObject(json);
        return object.getString("rates");
    }

}
