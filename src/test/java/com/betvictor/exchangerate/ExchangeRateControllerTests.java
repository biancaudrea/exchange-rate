package com.betvictor.exchangerate;

import com.betvictor.exchangerate.controller.ExchangeRateController;
import com.betvictor.exchangerate.service.ExchangeRateService;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangeRateControllerTests extends BaseRESTTest {

	@Mock
	private ExchangeRateService exchangeRateService;

	ExchangeRateController exchangeRateController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		exchangeRateController = new ExchangeRateController(exchangeRateService);
	}

	@Test
	public void getExchangeRateTest() throws JSONException {
		exchangeRateController.getExchangeRate(CURRENCY_USD, CURRENCY_RON);
	}

	@Test
	public void getAllExchangeRatesTest() throws JSONException {
		exchangeRateController.getAllExchangeRates(CURRENCY_USD);
	}

	@Test
	public void getValueConversionTest() throws JSONException {
		exchangeRateController.getValueConversion(CURRENCY_USD, CURRENCY_RON, AMOUNT);
	}

	@Test
	public void getAllValueConversionsTest() throws JSONException {
		exchangeRateController.getAllValueConversions(CURRENCY_USD, AMOUNT, SYMBOLS);
	}

}
