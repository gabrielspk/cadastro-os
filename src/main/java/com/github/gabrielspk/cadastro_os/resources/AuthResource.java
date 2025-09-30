package com.github.gabrielspk.cadastro_os.resources;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielspk.cadastro_os.dto.security.AccountCredentialsDTO;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;
import com.github.gabrielspk.cadastro_os.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication endpoint")
@RestController
@RequestMapping("/auth")
public class AuthResource {
	
	@Autowired
	AuthService service;
	
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Operation(summary = "Autentica um usuário e retorna o token")
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
		System.out.println("Email: " + credentials.getEmail() + ", Senha: " + credentials.getSenha());
		if (credentialsIsInvalid(credentials)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais inválidas");
		}

		var token = service.signIn(credentials);

		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("token está nulo");
		}

		return ResponseEntity.ok(token);
	}

	private boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
		return credentials == null || 
		       StringUtils.isBlank(credentials.getSenha()) ||
		       StringUtils.isBlank(credentials.getEmail());
	}
	
	@GetMapping("/debug/checkUser")
	public String checkUser() {
	    var userOpt = usuarioRepository.findByEmail("gabriel@email.com");
	    
	    if (userOpt.isEmpty()) {
	        return "User not found";
	    }
	    
	    Usuario user = userOpt.get(); // pega o usuário real do Optional
	    boolean matches = passwordEncoder.matches("123", user.getSenha());
	    
	    return "User found. Password matches? " + matches;
	}
	
}
