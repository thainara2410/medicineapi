package com.medicineapi.medicineapi.service;

import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.medicineapi.medicineapi.dto.CreateUserRoleDTO;
import com.medicineapi.medicineapi.enums.Status;
import com.medicineapi.medicineapi.models.RoleModel;
import com.medicineapi.medicineapi.models.UserModel;
import com.medicineapi.medicineapi.repository.RoleRepository;
import com.medicineapi.medicineapi.repository.UserRepository;

@Service
@Transactional
public class UserService{
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String gerarSenhaTemporaria() {
        String senhaAleatoria = RandomStringUtils.random(10, true, true);
        return senhaAleatoria;
    }

    public List<UserModel> listByStatus(Status status) {
        return userRepository.findByStatus(status);
    }
    
    public UserModel findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + id));
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o email: " + email));
    }

    public UserModel execute(UserModel user) {

        Optional<UserModel> existsUser = userRepository.findByEmail(user.getEmail());
    
        if (existsUser.isPresent()) {
          throw new Error("User already exists!");
          //ResponseEntity.status(HttpStatus.CONFLICT);
        }
    
        UserModel createdUser = userRepository.save(user);
    
        return createdUser;
      }

      public CreateUserRoleDTO criandoUser(long idUser, List<Long>idsRoles){
        CreateUserRoleDTO createUserRoleDTO = new CreateUserRoleDTO();
        createUserRoleDTO.setIdUser(idUser);
        createUserRoleDTO.setIdsRoles(idsRoles);
        return createUserRoleDTO;

      }


    
}
