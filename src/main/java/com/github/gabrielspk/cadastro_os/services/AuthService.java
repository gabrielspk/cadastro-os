package com.github.gabrielspk.cadastro_os.services;

import org.springframework.beans.factory.annotation.Autowired;
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

    public TokenDTO signIn(AccountCredentialsDTO credentials) {
        String email = credentials.getEmail();
        String senha = credentials.getSenha();
        
    	authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, senha));

        var usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));

        return tokenProvider.createAccessToken(email, usuario.getRoles());
    }
    
    public TokenDTO refreshToken(String refreshToken) {
    	
    	String username = tokenProvider.getUsernameFromToken(refreshToken);
    	
    	var usuario = repository
    			.findByEmail(username).
    			orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));
    	
    	return tokenProvider.createAccessToken(usuario.getEmail(), usuario.getRoles());
		}
	}
