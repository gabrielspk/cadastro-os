package com.github.gabrielspk.cadastro_os.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.security.AccountCredentialsDTO;
import com.github.gabrielspk.cadastro_os.dto.security.TokenDTO;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;
import com.github.gabrielspk.cadastro_os.security.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UsuarioRepository repository;

	public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getSenha()));

		var usuario = repository.findByEmail(credentials.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("E-mail não encontrado"));

		var token = tokenProvider.createAccessToken(credentials.getEmail(), usuario.getRoles());

		return ResponseEntity.ok(token);
	}

}
