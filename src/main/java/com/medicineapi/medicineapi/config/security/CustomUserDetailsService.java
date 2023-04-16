package com.medicineapi.medicineapi.config.security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medicineapi.medicineapi.models.UserModel;
import com.medicineapi.medicineapi.repository.UserRepository;


@Service
//@Transactional
public class CustomUserDetailsService implements UserDetailsService {

  
  final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserModel existsUser = userRepository.findByEmailFetchRoles(email);

/*
    UserModel existsUser = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username:" + email));
    
*/
    if(existsUser == null) {
      throw new Error("User does not exists!");
    } else {
      return UserPrincipal.create(existsUser);
    }
  }

}
