package com.radiobot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WeatherResponse {

  @JsonProperty("Temperature")
  Temperature temperature;
  @JsonProperty("RealFeelTemperature")
  RealFeelTemperature realFeelTemperature;
  @JsonProperty("IconPhrase")
  String iconPhrase;
  @JsonProperty("CloudCover")
  int cloudCover;
  @JsonProperty("RainProbability")
  int rainProbability;
  @JsonProperty("Wind")
  Wind wind;
  @JsonProperty("WindGust")
  WindGust windGust;
  @JsonProperty("UVIndexText")
  String uVIndexText;

  @Getter
  public static class Temperature {

    @JsonProperty("Value")
    double value;
  }

  @Getter
  public static class RealFeelTemperature {

    @JsonProperty("Value")
    double value;
  }

  @Getter
  public static class Wind {

    @JsonProperty("Speed")
    Speed speed;
    @JsonProperty("Direction")
    Direction direction;
  }

  @Getter
  public static class WindGust {

    @JsonProperty("Speed")
    Speed speed;
  }

  @Getter
  public static class Speed {

    @JsonProperty("Value")
    double value;
  }

  @Getter
  public static class Direction {

    @JsonProperty("Degrees")
    int degrees;
  }
}