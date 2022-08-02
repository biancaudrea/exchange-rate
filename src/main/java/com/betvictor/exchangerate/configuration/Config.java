package com.betvictor.exchangerate.configuration;

import com.betvictor.exchangerate.model.TrackRestData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.Queue;

@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Queue<TrackRestData> queue() {
        return new LinkedList<>();
    }
}
