package com.contoh.cv.config;


import com.contoh.cv.service.implementation.AppUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class AppSecurityConfiguration {

  private AppUserDetailService appUserDetailService;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
            .cors().disable()
            .csrf().disable()
            .authorizeRequests(auth ->
                    auth
                            .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/confirm/**").permitAll()
                            .anyRequest().authenticated())
            .userDetailsService(appUserDetailService)
            .httpBasic()
            .and()
            .build();
  }


  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager
          (AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
