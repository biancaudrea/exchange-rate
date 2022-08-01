package com.betvictor.exchangerate.controller;

import com.betvictor.exchangerate.service.RestCallService;
import io.swagger.v3.oas.annotations.Operation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {
    @Autowired
    private RestCallService restCallService;

    @Operation(summary = "Get exchange rate from Currency A to Currency B")
    @GetMapping("/exchange")
    public String getExchangeRate(@RequestParam(value = "from", required = true) final String from,
                                  @RequestParam(value = "to", required = true) final String to) throws JSONException {
        return restCallService.convertCurrencyAToB(from, to);
    }

    @Operation(summary = "Get all exchange rates from Currency A")
    @GetMapping("/exchange-all")
    public String getAllExchangeRates(@RequestParam(value = "from", required = true) final String from) throws JSONException {
        return restCallService.convertCurrencyToAllExchangeRates(from);
    }

    @Operation(summary = " Get value conversion from Currency A to Currency B")
    @GetMapping("/conversion")
    public String getValueConversion(@RequestParam(value = "from", required = true) final String from,
                                     @RequestParam(value = "to", required = true) final String to,
                                     @RequestParam(value = "amount", required = true) final Integer amount) throws JSONException {
        return restCallService.getValueConversion(from, to, amount);
    }

    @Operation(summary = " Get value conversion from Currency A to Currency B")
    @GetMapping("/conversion-all")
    public String getAllValueConversions(@RequestParam(value = "from", required = true) final String from,
                                     @RequestParam(value = "amount", required = true) final Integer amount,
                                         @RequestParam(value = "symbols", required = true) final String symbols) throws JSONException {
        return restCallService.getAllValueConversions(from, amount, symbols);
    }
}
