package com.contoh.cv.service.implementation;

import com.contoh.cv.entity.Role;
import com.contoh.cv.entity.User;
import com.contoh.cv.model.request.RegistrationRequest;
import com.contoh.cv.model.response.RegistrationResponse;
import com.contoh.cv.repository.RoleRepository;
import com.contoh.cv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private RoleRepository roleRepository;

  public RegistrationResponse registration(RegistrationRequest registrationRequest){

    User user = new User();
    BeanUtils.copyProperties(registrationRequest, user);
    user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

    List<Role> roles = new ArrayList<>();
    Role role = roleRepository.findById(1L).get();
    roles.add(role);
    user.setRoles(roles);

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


}
