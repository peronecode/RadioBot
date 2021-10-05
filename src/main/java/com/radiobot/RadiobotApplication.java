package com.radiobot;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RadiobotApplication {

  public static void main(String... args) {
    SpringApplication.run(RadiobotApplication.class, args);
  }

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
        .setSerializationInclusion(Include.NON_NULL)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .registerModule(new JavaTimeModule());
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}