package com.contoh.cv.service.implementation;

import com.contoh.cv.entity.User;
import com.contoh.cv.exception.CustomException;
import com.contoh.cv.model.AppUserDetail;
import com.contoh.cv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserDetailService implements UserDetailsService {

  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameOrEmail(username, username)
            .orElseThrow(() -> new CustomException(
                    "Username or Password Incorrect",
                    "INVALID_USERNAME_PASSWORD",
                    404));
    return new AppUserDetail(user);
  }
}
