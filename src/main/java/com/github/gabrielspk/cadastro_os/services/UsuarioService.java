package com.github.gabrielspk.cadastro_os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.UsuarioCreateDTO;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.exceptions.DatabaseException;
import com.github.gabrielspk.cadastro_os.exceptions.ResourceNotFoundException;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario fromCreateDTO(UsuarioCreateDTO dto) {
	    return new Usuario(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getTipoUsuario());
	}
	
	public List<Usuario> findAll() {
		return repository.findAll();
	}
	
	public Usuario findById(Long id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado. Id: " + id));
	}
	
	public Usuario Insert(Usuario obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Usuário não encontrado. Id: " + id);
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    return repository.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Email " + username + " não encontrado"));
	}
}
