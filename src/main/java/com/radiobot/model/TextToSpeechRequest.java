package com.radiobot.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import lombok.Value;

@Value
public class TextToSpeechRequest {

  Input input;
  Voice voice = new Voice();
  AudioConfig audioConfig = new AudioConfig();

  public TextToSpeechRequest(WeatherResponse weatherResponse) {
    this.input = convertSpeech(weatherResponse);
  }

  @Value
  private static class Input {

    String text;
  }

  @Value
  private static class Voice {

    String languageCode = "pt-PT";
    String name = "pt-PT-Wavenet-D";
    String ssmlGender = "FEMALE";
  }

  @Value
  private static class AudioConfig {

    String audioEncoding = "MP3";
    Double speakingRate = 0.93;
    Double pitch = -5.0;
    String[] effectsProfileId = new String[]{"large-home-entertainment-class-device"};
  }

  private Input convertSpeech(WeatherResponse weatherResponse) {
    String templateText =
        "Previsão do tempo. Lisboa, %s, dia %s de %s de %s, hora atual %s horas. "
            + "A temperatura atual em Lisboa é de %s graus, com sensação térmica de %s graus. "
            + "O céu está %s neste exato momento. Cobertura de nuvens de %s porcento. "
            + "Com probabilidades de chuva de %s porcento."
            + "A velocidade do vento é de %s quilometros por hora, em direção a %s graus, com rajadas de até %s quilometros por hora. "
            + "O índice solar atual é %s. "
            + "Estação Águia. Previsões do tempo de hora em hora. Tenham um bom dia.";

    Locale portugalLocale = new Locale("pt", "PT");
    LocalDateTime nowDate = LocalDateTime.now(ZoneId.of("Portugal"));

    String dayOfWeek = nowDate.getDayOfWeek().getDisplayName(TextStyle.FULL, portugalLocale);
    String monthName = nowDate.getMonth().getDisplayName(TextStyle.FULL, portugalLocale);
    int dayOfMonth = nowDate.getDayOfMonth();
    int year = nowDate.getYear();
    int hour = nowDate.getHour();

    String speechText = String.format(templateText,
        dayOfWeek, dayOfMonth, monthName, year, hour,
        weatherResponse.getTemperature().getValue(),
        weatherResponse.getRealFeelTemperature().getValue(),
        weatherResponse.getIconPhrase(),
        weatherResponse.getCloudCover(),
        weatherResponse.getRainProbability(),
        weatherResponse.getWind().getSpeed().getValue(),
        weatherResponse.getWind().getDirection().getDegrees(),
        weatherResponse.getWindGust().getSpeed().getValue(),
        weatherResponse.getUVIndexText()
    );

    return new Input(speechText);
  }
}