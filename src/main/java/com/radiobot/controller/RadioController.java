package com.radiobot.controller;

import com.radiobot.component.RadioComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class RadioController {

  @Autowired
  RadioComponent radioComponent;

  @GetMapping("/execute")
  public ResponseEntity<String> execute() {
    return radioComponent.execute();
  }
}