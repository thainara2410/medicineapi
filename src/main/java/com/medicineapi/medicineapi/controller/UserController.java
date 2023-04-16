package com.medicineapi.medicineapi.controller;

import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.medicineapi.medicineapi.dto.CreateUserRoleDTO;
import com.medicineapi.medicineapi.dto.LoginDTO;
import com.medicineapi.medicineapi.enums.Status;
import com.medicineapi.medicineapi.models.RoleModel;
import com.medicineapi.medicineapi.models.UserModel;
import com.medicineapi.medicineapi.repository.RoleRepository;
import com.medicineapi.medicineapi.repository.UserRepository;
import com.medicineapi.medicineapi.service.CreateRoleUserService;
import com.medicineapi.medicineapi.service.EmailSenderService;
import com.medicineapi.medicineapi.service.UserService;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

    final UserRepository userRepository;
    final UserService userService;
    final PasswordEncoder passwordEncoder;
    final EmailSenderService emailSenderService;
    final AuthenticationManager authenticationManager;
    final RoleRepository roleRepository;
    final CreateRoleUserService createRoleUserService;
    
    public UserController(UserRepository userRepository, UserService userService, AuthenticationManager authenticationManager, 
                            PasswordEncoder passwordEncoder, EmailSenderService emailSenderService, RoleRepository roleRepository,
                            CreateRoleUserService createRoleUserService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.createRoleUserService = createRoleUserService;
    }

    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    @PostMapping("/criar")
    public ResponseEntity<Void> createUser(@RequestBody UserModel user) {
        UserModel userModel = userService.execute(user);
        String senhaTemp = userService.gerarSenhaTemporaria();
        // Cria um novo usuário
        userModel.setPassword(senhaTemp);
        userModel.setStatus(Status.PENDENTE);
        userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @CrossOrigin(origins = "*")
    //@PreAuthorize("permitAll()")
    @PostMapping("/role")
    public UserModel role(@RequestBody CreateUserRoleDTO createUserRoleDTO){
        return createRoleUserService.execute(createUserRoleDTO);
    }

    @PostMapping("/role/criar")
    public RoleModel saveRole(RoleModel role) {
        return roleRepository.save(role);
    }

    @CrossOrigin(origins = "*")
    @PreAuthorize("permitAll()")
    @PostMapping("/aprovar")
    public ResponseEntity<Void> aprovarUsuario(@RequestBody UserModel usuario) throws MessagingException {

        usuario.setStatus(Status.APROVADO);
        emailSenderService.enviarSenhaTemporaria(usuario.getEmail(), usuario.getPassword());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        userRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO credenciais){
        String email = credenciais.getEmail();
        String senha = credenciais.getPassword();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                email, senha));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //String token = jwtGenerator.generateToken(authentication);
        //return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        return new ResponseEntity<>("User signed sucess", HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pendente")
    @PreAuthorize("permitAll()")
    public List<UserModel> listPendingUsers() {
        return userService.listByStatus(Status.PENDENTE);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{email}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserModel> getUsuario(@PathVariable String email) {
        Optional<UserModel> usuarioOptional = userRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            UserModel usuario = usuarioOptional.get();
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}
