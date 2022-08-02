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
    public RestCallService(RestTemplate restTemplate, Queue<TrackRestData> exchangeQueue) {
        this.restTemplate = restTemplate;
        this.exchangeQueue = exchangeQueue;
    }

    private RestTemplate restTemplate;

    private Queue<TrackRestData> exchangeQueue;

    /**
     * Method makes GET call to exchangerate.host for fetching exchange-rates and returns the result
     *
     * @param url - url for making external REST calls
     *
     * @return - result returned by the external REST call
     *
     * @throws JSONException for parsing JSON response
     */
    public String performGetRequest(String url) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String json = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        JSONObject object = new JSONObject(json);
        String result = url.contains("latest") ? object.getString("rates") : object.getString("result");
        if (Objects.equals(result, "null")) {
            throw new EmptyServerResponse("Null message from server, check input parameters");
        }
        exchangeQueue.add(new TrackRestData(LocalDateTime.now(), url, result));
        return result;
    }

}
