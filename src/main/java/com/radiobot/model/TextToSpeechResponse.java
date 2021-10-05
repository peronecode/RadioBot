package com.radiobot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TextToSpeechResponse {

  @JsonProperty("audioContent")
  String audioContent;
}