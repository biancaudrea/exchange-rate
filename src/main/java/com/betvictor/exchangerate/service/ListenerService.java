package com.betvictor.exchangerate.service;

import com.betvictor.exchangerate.model.TrackRestData;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Queue;

@Service
public class ListenerService {
    @Autowired
    private Queue<TrackRestData> exchangeQueue;

    @Autowired
    RestCallService restCallService;

    @Value("${base.url.exchange}")
    private String exchangeBaseUrl;

    @Value("${base.url.exchange.all}")
    private String exchangeAllBaseUrl;

    @Value("${base.url.conversion}")
    private String conversionBaseUrl;

    @Value("${base.url.conversion.all}")
    private String conversionAllBaseUrl;

    public String convertCurrencyAToB(String from, String to) throws JSONException {
        String url = exchangeBaseUrl + from + "&to=" + to;
        return checkQueue(url);
    }

    public String convertCurrencyToAllExchangeRates(String currency) throws JSONException {
        String url = exchangeAllBaseUrl + currency;
        return checkQueue(url);
    }

    public String getValueConversion(String from, String to, Integer amount) throws JSONException {
        String url = conversionBaseUrl + from + "&to=" + to + "&amount=" + amount;
        return checkQueue(url);
    }

    public String getAllValueConversions(String currency, Integer amount, String symbols) throws JSONException {
        String url = conversionAllBaseUrl + currency + "&amount=" + amount + "&symbols=" + symbols;
        return checkQueue(url);
    }

    /**
     *
     * @param url - url for REST call
     * @return result of REST call
     * @throws JSONException
     * the method is used as a mechanism to do as little calls as possible to the external provider,
     * if same url has been called in less than a minute ago, the result is in the queue and can be
     * provided, without making another external call
     */
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
