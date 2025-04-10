package com.backend.elex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.elex.entity.UserEntity;
import com.backend.elex.security.JwtUtil;
import com.backend.elex.service.UserService;
import com.backend.elex.vo.UserRegistrationRequestVO;
import com.backend.elex.vo.UserVO;

@RestController
@RequestMapping("backendelex/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	
	public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping
	public ResponseEntity<List<UserVO>> getAllUsers(){
	    System.out.println("Petición recibida en /users");
	    List<UserVO> users = userService.listUsers();
	    System.out.println("Enviando usuarios al frontend: " + users.size());
	    return ResponseEntity.ok(users);
	}

	
	@GetMapping("/{id}")
    public ResponseEntity<UserVO> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
	@GetMapping("/dni/{dni}")
	public ResponseEntity<UserVO> getUserByDni(@PathVariable String dni){
		return userService.getUserByDni(dni)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
    public ResponseEntity<UserVO> registerUser(@RequestBody UserRegistrationRequestVO request) {
        return ResponseEntity.ok(userService.registerUser(request.getUser(), request.getPassword()));
    }
	
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
	    String email = request.get("email");
	    String rawPassword = request.get("password");

	    System.out.println("Intento de login:");
	    System.out.println("Email recibido: " + email);
	    System.out.println("Contraseña recibida: " + rawPassword);

	    UserEntity user = userService.getUserByEmail(email)
	            .orElseThrow(() -> {
	                System.out.println("Usuario no encontrado para email: " + email);
	                return new UsernameNotFoundException("Usuario no encontrado");
	            });

	    System.out.println("Contraseña en BD: " + user.getPassword());

	    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
	        System.out.println("Las contraseñas NO coinciden");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Credenciales incorrectas");
	    }

	    System.out.println("Las contraseñas COINCIDEN");

	    String token = jwtUtil.generateToken(email, user.getName());

	    return ResponseEntity.ok(Map.of("token", token));
	}


	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
	        userService.deleteUserById(id);
	        return ResponseEntity.noContent().build();
	    }
	 
	 
	 @DeleteMapping("/dni/{dni}")
	    public ResponseEntity<Void> deleteUserByDni(@PathVariable String dni) {
	        userService.deleteUserByDni(dni);
	        return ResponseEntity.noContent().build();
	    }
}
