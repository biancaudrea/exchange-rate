package com.betvictor.exchangerate;

import com.betvictor.exchangerate.exception.EmptyServerResponse;
import com.betvictor.exchangerate.model.TrackRestData;
import com.betvictor.exchangerate.service.ExchangeRateService;
import com.betvictor.exchangerate.service.RestCallService;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.Queue;

@SpringBootTest
public class ExchangeRateServiceTests extends BaseRESTTest {
    ExchangeRateService exchangeRateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        RestTemplate restTemplate = new RestTemplate();
        Queue<TrackRestData> exchangeQueue = new LinkedList<>();
        RestCallService restCallService = new RestCallService(restTemplate, exchangeQueue);
        exchangeRateService = new ExchangeRateService(exchangeQueue, restCallService);
        ReflectionTestUtils.setField(exchangeRateService, "exchangeBaseUrl", "https://api.exchangerate.host/convert?amount=1&places=2&from=");
        ReflectionTestUtils.setField(exchangeRateService, "exchangeAllBaseUrl", "https://api.exchangerate.host/latest?amount=1&places=2&base=");
        ReflectionTestUtils.setField(exchangeRateService, "conversionBaseUrl", "https://api.exchangerate.host/convert?places=2&from=");
        ReflectionTestUtils.setField(exchangeRateService, "conversionAllBaseUrl", "https://api.exchangerate.host/latest?places=2&base=");

    }

    @Test
    public void convertCurrencyAToBSuccessTest() throws JSONException {
        String result = exchangeRateService.convertCurrencyAToB(CURRENCY_USD, CURRENCY_RON);
        Assertions.assertNotNull(result);
    }

    @Test
    public void convertCurrencyAToBWrongSymbolsTest() throws JSONException {
        Assertions.assertThrows(EmptyServerResponse.class, () ->
                exchangeRateService.convertCurrencyAToB(WRONG_CURRENCY_A, WRONG_CURRENCY_B)
        );
    }

    @Test
    public void convertCurrencyToAllExchangeRatesSuccessTest() throws JSONException {
        String result = exchangeRateService.convertCurrencyToAllExchangeRates(CURRENCY_USD);
        Assertions.assertNotNull(result);
    }

    @Test
    public void getValueConversionSuccessTest() throws JSONException {
        String result = exchangeRateService.getValueConversion(CURRENCY_USD, CURRENCY_EUR, AMOUNT);
        Assertions.assertNotNull(result);
    }

    @Test
    public void getValueConversionWrongSymbolsTest() throws JSONException {
        Assertions.assertThrows(EmptyServerResponse.class, () ->
                exchangeRateService.getValueConversion(WRONG_CURRENCY_A, WRONG_CURRENCY_B, AMOUNT)
        );
    }

    @Test
    public void getAllValueConversionsSuccessTest() throws JSONException {
        String result = exchangeRateService.getAllValueConversions(CURRENCY_EUR, AMOUNT, SYMBOLS);
        Assertions.assertNotNull(result);
    }
}
