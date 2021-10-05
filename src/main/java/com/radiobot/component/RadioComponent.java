package com.radiobot.component;

import com.radiobot.APIKeys;
import com.radiobot.model.TextToSpeechRequest;
import com.radiobot.model.TextToSpeechResponse;
import com.radiobot.model.WeatherResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RadioComponent {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private APIKeys apiKeys;

  public ResponseEntity<String> execute() {
    WeatherResponse weatherResponse = getWeather();
    TextToSpeechRequest textToSpeechRequest = new TextToSpeechRequest(weatherResponse);
    String audioBase64 = getSpeech(textToSpeechRequest);

    playLocalAudio(audioBase64);

    return new ResponseEntity<>(audioBase64, HttpStatus.OK);
  }

  private WeatherResponse getWeather() {
    String url = "http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/274087";
    url = String.format(url + "?apikey=%s&language=pt-pt&details=true&metric=true", apiKeys.getAccuweather());
    ResponseEntity<WeatherResponse[]> response = restTemplate.getForEntity(url, WeatherResponse[].class);

    if (response.getStatusCode().equals(HttpStatus.OK)) {
      if (Objects.nonNull(response.getBody())) {
        return response.getBody()[0];
      } else {
        throw new InternalError("Could get the weather data. No request body was found.");
      }
    } else {
      throw new HttpServerErrorException(response.getStatusCode());
    }
  }

  private String getSpeech(TextToSpeechRequest textToSpeechRequest) {
    String url = String.format("https://texttospeech.googleapis.com/v1/text:synthesize?key=%s", apiKeys.getGcloud());
    ResponseEntity<TextToSpeechResponse> response = restTemplate.postForEntity(url, textToSpeechRequest, TextToSpeechResponse.class);

    if (response.getStatusCode().equals(HttpStatus.OK)) {
      if (Objects.nonNull(response.getBody())) {
        return response.getBody().getAudioContent();
      } else {
        throw new InternalError("Could not convert text to speech. No request body was found.");
      }
    } else {
      throw new HttpServerErrorException(response.getStatusCode(),
          String.format("Wrong http response status. Actual: %s. Expected: %s.", response.getStatusCode(), HttpStatus.OK));
    }
  }

  private void playLocalAudio(String audioBase64) {
    String audioFilePath = "speech.mp3";
    try (FileOutputStream stream = new FileOutputStream(audioFilePath)) {
      byte[] decoded = Base64.getDecoder().decode(audioBase64);
      stream.write(decoded);

      File audioFile = new File(audioFilePath);
      String playAudioCommand = "afplay %s";
      Runtime.getRuntime().exec(new String[]{"bash", "-c", String.format(playAudioCommand, audioFile)});

      boolean isFileDeleted = audioFile.delete();
      log.info("Audio delete: " + isFileDeleted);
    } catch (IOException e) {
      log.error("Cannot local execute audio.", e);
    }
  }
}