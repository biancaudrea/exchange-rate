package com.betvictor.exchangerate.service;

import com.betvictor.exchangerate.model.TrackRestData;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Queue;

@Service
public class ExchangeRateService {
    private Queue<TrackRestData> exchangeQueue;
    RestCallService restCallService;

    @Autowired
    public ExchangeRateService(Queue<TrackRestData> exchangeQueue, RestCallService restCallService){
        this.exchangeQueue = exchangeQueue;
        this.restCallService = restCallService;
    }

    @Value("${base.url.exchange}")
    private String exchangeBaseUrl;

    @Value("${base.url.exchange.all}")
    private String exchangeAllBaseUrl;

    @Value("${base.url.conversion}")
    private String conversionBaseUrl;

    @Value("${base.url.conversion.all}")
    private String conversionAllBaseUrl;

    /**
     * Get exchange rate from Currency A to Currency B
     *
     * @param from - currency A
     *
     * @param to - currency B
     *
     * @return - exchange rate
     *
     * @throws JSONException for parsing JSON response
     */
    public String convertCurrencyAToB(String from, String to) throws JSONException {
        String url = exchangeBaseUrl + from + "&to=" + to;
        return checkQueue(url);
    }

    /**
     * Get all exchange rates from Currency A
     *
     * @param currency - currency A
     *
     * @return - list of exchange rates
     *
     * @throws JSONException for parsing JSON response
     */
    public String convertCurrencyToAllExchangeRates(String currency) throws JSONException {
        String url = exchangeAllBaseUrl + currency;
        return checkQueue(url);
    }

    /**
     * Get value conversion from Currency A to Currency B
     *
     * @param from - currency A
     *
     * @param to - currency B
     *
     * @param amount - amount to be converted
     *
     * @return - value converted
     *
     * @throws JSONException for parsing JSON response
     */
    public String getValueConversion(String from, String to, Integer amount) throws JSONException {
        String url = conversionBaseUrl + from + "&to=" + to + "&amount=" + amount;
        return checkQueue(url);
    }

    /**
     * Get value conversion from Currency A to a list of supplied currencies
     *
     * @param currency - currency A
     * @param amount - amount to be converted
     * @param symbols - list of currency symbols separated by comma
     * @return - list of converted values
     * @throws JSONException for parsing JSON response
     */
    public String getAllValueConversions(String currency, Integer amount, String symbols) throws JSONException {
        String url = conversionAllBaseUrl + currency + "&amount=" + amount + "&symbols=" + symbols;
        return checkQueue(url);
    }

    /**
     * the method is used as a mechanism to do as little calls as possible to the external provider,
     * if same url has been called in less than a minute ago, the result is in the queue and can be
     * provided, without making another external call
     *
     * @param url - url for REST call
     *
     * @return result of REST call
     *
     * @throws JSONException for parsing JSON response
     */
    private String checkQueue(String url) throws JSONException {
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
