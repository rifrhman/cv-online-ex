package com.contoh.cv.controller;

import com.contoh.cv.model.request.RegistrationRequest;
import com.contoh.cv.model.response.RegistrationResponse;
import com.contoh.cv.service.implementation.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RegistrationController {

  private RegistrationService registrationService;

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest registrationRequest){
    RegistrationResponse response = registrationService.registration(registrationRequest);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

}
