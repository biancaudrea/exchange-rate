package com.betvictor.exchangerate.controller;

import com.betvictor.exchangerate.service.RestCallService;
import io.swagger.v3.oas.annotations.Operation;
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
    public String getExchangeRateFromCurrencyAToCurrencyB(@RequestParam(value = "from", required = true) final String from,
                                                          @RequestParam(value = "to", required = true) final String to) {
        return restCallService.convertCurrency();
    }
}
