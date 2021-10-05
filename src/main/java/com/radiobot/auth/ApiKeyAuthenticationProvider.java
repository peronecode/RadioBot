package com.radiobot.auth;

import com.radiobot.APIKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private APIKeys apiKeys;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String apiKey = (String) authentication.getPrincipal();
    if (ObjectUtils.isEmpty(apiKey)) {
      throw new InsufficientAuthenticationException("No API key in request");
    } else {
      if (apiKeys.getRadiobot().equals(apiKey)) {
        return new ApiKeyAuthenticationToken(apiKey, true);
      }
      throw new BadCredentialsException("API Key is invalid");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
  }
}