package com.betvictor.exchangerate.service;

import com.betvictor.exchangerate.exception.EmptyServerResponse;
import com.betvictor.exchangerate.model.TrackRestData;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;

@Service
public class RestCallService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Queue<TrackRestData> exchangeQueue;

    /**
     *
     * @param url - url for making external REST calls
     * @return - result returned by the external REST call
     * @throws JSONException
     * Method makes GET call to exchangerate.host for fetching exchange-rates and returns the result
     */
    public String performGetRequest(String url) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        JSONObject object = new JSONObject(json);
        String result;
        if (url.contains("latest")) {
            result = object.getString("rates");
        } else {
            result = object.getString("result");
        }
        if (!Objects.equals(result, "null")) {
            exchangeQueue.add(new TrackRestData(LocalDateTime.now(), url, result));
            return result;
        } else throw new EmptyServerResponse("Null message from server, check input parameters");

    }

}
