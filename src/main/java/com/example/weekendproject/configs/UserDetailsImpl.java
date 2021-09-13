package com.example.weekendproject.configs;

import com.example.weekendproject.model.Role;
import com.example.weekendproject.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

  private String userName;
  private String password;
  private boolean isActive;
  private Collection<GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return isActive;
  }

  public UserDetailsImpl(User user) {
    this.password = user.getPassword();
    this.userName = user.getUsername();
    this.isActive = user.isActive();
    this.authorities = userRoles(user.getRoles());
  }

  public Collection<GrantedAuthority> userRoles(Set<Role> roleSet) {
    Collection<GrantedAuthority> result = new HashSet<>();
    for (Role role : roleSet) {
      result.add(new SimpleGrantedAuthority(role.getRole()));
    }
    return result;
  }
}
