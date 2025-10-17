package com.github.gabrielspk.cadastro_os.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.security.AccountCredentialsDTO;
import com.github.gabrielspk.cadastro_os.dto.security.TokenDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioDTO;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.exceptions.DuplicateUserException;
import com.github.gabrielspk.cadastro_os.exceptions.InvalidCredentialsException;
import com.github.gabrielspk.cadastro_os.exceptions.InvalidJwtAuthenticationException;
import com.github.gabrielspk.cadastro_os.exceptions.RequiredObjectIsNullException;
import com.github.gabrielspk.cadastro_os.mappers.UsuarioMapper;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;
import com.github.gabrielspk.cadastro_os.security.JwtTokenProvider;

@Service
public class AuthService {
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private UsuarioMapper usuarioMapper;

    public TokenDTO signIn(AccountCredentialsDTO credentials) {
        String email = credentials.getEmail();
        String senha = credentials.getSenha();
        
        if(isCredentialsInvalid(credentials)) {
        	throw new InvalidCredentialsException("Credenciais inválidas");
        }
        
    	authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, senha));

        var usuario = getUserByEmail(email);
        return tokenProvider.createAccessToken(email, usuario.getRoles());
    }
    
	private boolean isCredentialsInvalid(AccountCredentialsDTO credentials) {
		return credentials == null 
				|| StringUtils.isBlank(credentials.getSenha())
				|| StringUtils.isBlank(credentials.getEmail());
	}
    
	public TokenDTO refreshToken(String refreshToken) {
		if (isTokenNull(refreshToken)) {
			throw new InvalidJwtAuthenticationException("Token de atualização ausente");
		}
		
		return tokenProvider.refreshToken(refreshToken);
	}
	
    private boolean isTokenNull(String token) {
        return StringUtils.isBlank(token);
    }
	
	private Usuario getUserByEmail(String email) {
	    return repository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}
	
	public UsuarioDTO create(UsuarioCreateDTO dto) {
		logger.info("Criando um novo usuário!");
		
		if (dto == null) throw new RequiredObjectIsNullException();
		var usuario = usuarioMapper.fromCreateDTO(dto);
		
	    if (repository.findByEmail(dto.getEmail()).isPresent()) {
	        throw new DuplicateUserException("Já existe um usuário cadastrado com este e-mail");
	    }
		
		usuario.setSenha(generateHashedPassword(dto.getSenha()));
		
	    usuario.setAccountNonExpired(true);
	    usuario.setAccountNonLocked(true);
	    usuario.setCredentialsNonExpired(true);
	    usuario.setEnabled(true);
	    
	    usuario = repository.save(usuario);
		logger.info("Usuário criado com sucesso: {}", usuario.getEmail());
	    
	    return usuarioMapper.toDTO(usuario);
	}
	
	private String generateHashedPassword(String password) {
	    PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
	            "", 8, 185000,
	            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

	    Map<String, PasswordEncoder> encoders = new HashMap<>();
	    encoders.put("pbkdf2", pbkdf2Encoder);
	    DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
	    passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

	    return passwordEncoder.encode(password);
	}
}