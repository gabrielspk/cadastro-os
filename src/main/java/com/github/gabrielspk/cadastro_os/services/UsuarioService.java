package com.github.gabrielspk.cadastro_os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.UsuarioCreateDTO;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;
import com.github.gabrielspk.cadastro_os.services.exceptions.DatabaseException;
import com.github.gabrielspk.cadastro_os.services.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {
	
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
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Usuario Insert(Usuario obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	
	
}
