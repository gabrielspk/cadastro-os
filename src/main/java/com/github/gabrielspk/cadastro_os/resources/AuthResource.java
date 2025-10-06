package com.github.gabrielspk.cadastro_os.resources;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielspk.cadastro_os.dto.security.AccountCredentialsDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioDTO;
import com.github.gabrielspk.cadastro_os.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Authentication endpoint")
@RestController
@RequestMapping("/auth")
public class AuthResource {
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	AuthService service;
	
	@Operation(summary = "Autentica um usuário e retorna o token")
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
		if (credentialsIsInvalid(credentials)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais inválidas");
		}

		var token = service.signIn(credentials);

		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida");
		}

		return ResponseEntity.ok(token);
	}
	
	@Operation(summary = "Renova o token para um usuário autenticado")
	@PutMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
		if (parametersAreInvalid(refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida");
		var token = service.refreshToken(refreshToken);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Requisição inválida");
		}
		return ResponseEntity.ok(token);
	}
	
	@Operation(summary = "Cria um novo usuário")
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody UsuarioCreateDTO dto) {
		logger.info("Recebida requisição de criação de usuário: {}", dto.getEmail());
		UsuarioDTO created = service.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	
	private boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
		return credentials == null || 
		       StringUtils.isBlank(credentials.getSenha()) ||
		       StringUtils.isBlank(credentials.getEmail());
	}
	
    private boolean parametersAreInvalid(String refreshToken) {
        return StringUtils.isBlank(refreshToken);
    }  
}
