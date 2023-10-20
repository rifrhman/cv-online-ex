package com.contoh.cv.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 30)
  private String username;
  @Column(length = 30)
  private String email;
  private String password;
  @Column(name = "is_active")
  private boolean isActive = false;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "role_user",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles;

}
