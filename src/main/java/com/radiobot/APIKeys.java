package com.radiobot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APIKeys {

  @Value("${radiobot.api-keys.radiobot}")
  private String radiobot;

  @Value("${radiobot.api-keys.accuweather}")
  private String accuweather;

  @Value("${radiobot.api-keys.gcloud}")
  private String gcloud;

  public String getRadiobot() {
    return radiobot;
  }

  public String getAccuweather() {
    return accuweather;
  }

  public String getGcloud() {
    return gcloud;
  }
}