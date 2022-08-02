package com.betvictor.exchangerate;

import com.betvictor.exchangerate.service.ListenerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExchangeRateApplicationTests {

	@Mock
	private ListenerService listenerService;

	@Test
	void contextLoads() {
	}

}
