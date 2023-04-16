package com.medicineapi.medicineapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.medicineapi.medicineapi.dto.CreateUserRoleDTO;
import com.medicineapi.medicineapi.models.RoleModel;
import com.medicineapi.medicineapi.models.UserModel;
import com.medicineapi.medicineapi.repository.UserRepository;


@Service
public class CreateRoleUserService {

  @Autowired
  UserRepository userRepository;

  public UserModel execute(CreateUserRoleDTO createUserRoleDTO) {

    UserModel userExists = userRepository.findById(createUserRoleDTO.getIdUser())
            .orElseThrow(()-> new UsernameNotFoundException("User Not Found with id:" + createUserRoleDTO.getIdUser()));
    List<RoleModel> roles = new ArrayList<>();


    roles = createUserRoleDTO.getIdsRoles().stream().map(role -> {
      return new RoleModel(role);
    }).collect(Collectors.toList());

      userExists.setRoles(roles);

      userRepository.save(userExists);

      return userExists;
    

    

  }
}
