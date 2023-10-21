package com.contoh.cv.service.implementation;

import com.contoh.cv.entity.Role;
import com.contoh.cv.entity.User;
import com.contoh.cv.exception.CustomException;
import com.contoh.cv.model.request.RegistrationRequest;
import com.contoh.cv.model.response.RegistrationResponse;
import com.contoh.cv.repository.RoleRepository;
import com.contoh.cv.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RegistrationService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private EmailService emailService;
  @Value("${app.baseUrl}")
  private String url;

  public RegistrationResponse registration(RegistrationRequest registrationRequest){

    if(userRepository.existsByUsernameContains(registrationRequest.getUsername())){
       throw new CustomException(
              "Username Already Exist",
              "USERNAME_INCORRECT",
              400
      );
    }
    if(userRepository.existsByEmail(registrationRequest.getEmail())){
      throw new CustomException(
              "Email Already Exist",
              "EMAIL_INCORRECT",
              400
      );
    }

    User user = new User();
    BeanUtils.copyProperties(registrationRequest, user);
    user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

    List<Role> roles = new ArrayList<>();
    Role role = roleRepository.findById(1L).get();
    roles.add(role);
    user.setRoles(roles);

    user.setConfirmationToken(generateToken());

    if(user.getConfirmationToken() != null){
      confirmToken(user);
    }

    userRepository.save(user);
    return mappingUserToRegistrationResponse(user);

  }

  public RegistrationResponse mappingUserToRegistrationResponse(User user){
    RegistrationResponse response = new RegistrationResponse();
    BeanUtils.copyProperties(user, response);
    response.setUsername(user.getUsername());
    response.setMessage("Registrasi berhasil!");

    return response;
  }

  public String generateToken(){
    return UUID.randomUUID().toString();
  }

  public void confirmToken(User user){

    String confirm = user.getConfirmationToken();
    String to = user.getEmail();
    String subject = "Activated your Account - CV Online App";
    String text = "Welcome "+ user.getUsername() + "\n" +
            "To Activate your account\n" +
            "Click this link : " + url + "/api/confirm?token=" + confirm;

    if(confirm != null){
      emailService.sendSimpleEmail(to, subject, text);
    } else{
      throw new CustomException(
              "Email Not Send",
              "EMAIL_FAILED_SEND",
              500
      );
    }

  }
  public boolean confirmEmail(String token) {
    User user = userRepository.findByConfirmationToken(token);

    if (user != null) {
      user.setConfirmationToken(null);
      user.setActive(true);
      userRepository.save(user);
      return true;
    }

    return false;
  }

}
