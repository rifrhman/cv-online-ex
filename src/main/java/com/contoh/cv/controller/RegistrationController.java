package com.contoh.cv.controller;

import com.contoh.cv.model.request.RegistrationRequest;
import com.contoh.cv.model.response.RegistrationResponse;
import com.contoh.cv.service.implementation.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RegistrationController {

  private RegistrationService registrationService;

  @GetMapping("/confirm")
  public ResponseEntity<String> confirmEmail(@RequestParam("token") String token){
    boolean confirm = registrationService.confirmEmail(token);

    if (confirm) {
      return new ResponseEntity<>("Email telah berhasil dikonfirmasi. Akun Anda sekarang aktif.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Gagal mengkonfirmasi email.", HttpStatus.BAD_REQUEST);
    }

  }

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest registrationRequest){
    RegistrationResponse response = registrationService.registration(registrationRequest);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

}
