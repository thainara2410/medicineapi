package com.medicineapi.medicineapi.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.medicineapi.medicineapi.models.UserModel;

public class UserPrincipal implements UserDetails {

  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(UserModel user) {
    this.username = user.getEmail();
    this.password = user.getPassword();

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    // ROLE_ADMIN , ROLE_USER ADMIN, USER ....
    authorities = user.getRoles().stream().map(role -> {
      return new SimpleGrantedAuthority(role.getName());
    }).collect(Collectors.toList());

    this.authorities = authorities;
    System.out.println(this.authorities);
  }

  public static UserPrincipal create(UserModel user) {
    return new UserPrincipal(user);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
