package com.betvictor.exchangerate.controller;

import com.betvictor.exchangerate.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Operation(summary = "Get exchange rate from Currency A to Currency B")
    @GetMapping("/exchange")
    public String getExchangeRate(@RequestParam(value = "from", required = true) final String from,
                                  @RequestParam(value = "to", required = true) final String to) throws JSONException {
        return exchangeRateService.convertCurrencyAToB(from, to);
    }

    @Operation(summary = "Get all exchange rates from Currency A")
    @GetMapping("/exchange-all")
    public String getAllExchangeRates(@RequestParam(value = "from", required = true) final String from) throws JSONException {
        return exchangeRateService.convertCurrencyToAllExchangeRates(from);
    }

    @Operation(summary = " Get value conversion from Currency A to Currency B")
    @GetMapping("/conversion")
    public String getValueConversion(@RequestParam(value = "from", required = true) final String from,
                                     @RequestParam(value = "to", required = true) final String to,
                                     @RequestParam(value = "amount", required = true) final Integer amount) throws JSONException {
        return exchangeRateService.getValueConversion(from, to, amount);
    }

    @Operation(summary = " Get value conversion from Currency A to Currency B")
    @GetMapping("/conversion-all")
    public String getAllValueConversions(@RequestParam(value = "from", required = true) final String from,
                                         @RequestParam(value = "amount", required = true) final Integer amount,
                                         @RequestParam(value = "symbols", required = true) final String symbols) throws JSONException {
        return exchangeRateService.getAllValueConversions(from, amount, symbols);
    }
}
