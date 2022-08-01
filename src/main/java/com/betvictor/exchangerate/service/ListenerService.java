package com.betvictor.exchangerate.service;

import com.betvictor.exchangerate.model.TrackRestData;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Queue;

@Service
public class ListenerService {
    @Autowired
    private Queue<TrackRestData> exchangeQueue;

    @Autowired
    RestCallService restCallService;

    public String convertCurrencyAToB(String from, String to) throws JSONException {
        String url = "https://api.exchangerate.host/convert?amount=1&places=2&from=" + from + "&to=" + to;
        return checkQueue(url);
    }

    public String convertCurrencyToAllExchangeRates(String currency) throws JSONException {
        String url = "https://api.exchangerate.host/latest?amount=1&places=2&base=" + currency;
        return checkQueue(url);
    }

    public String getValueConversion(String from, String to, Integer amount) throws JSONException {
        String url = "https://api.exchangerate.host/convert?places=2&from=" + from + "&to=" + to + "&amount=" + amount;
        return checkQueue(url);
    }

    public String getAllValueConversions(String currency, Integer amount, String symbols) throws JSONException {
        String url = "https://api.exchangerate.host/latest?places=2&base=" + currency + "&amount=" + amount + "&symbols=" + symbols;
        return checkQueue(url);
    }

    public String checkQueue(String url) throws JSONException {
        for (TrackRestData trackRestData : exchangeQueue) {
            if (url.equals(trackRestData.getUrl())) {
                if (trackRestData.getTimestamp().isBefore(LocalDateTime.now())) {
                    return trackRestData.getResult();
                } else {
                    exchangeQueue.remove(trackRestData);
                }
            }
        }
        return restCallService.performGetRequest(url);
    }
}
