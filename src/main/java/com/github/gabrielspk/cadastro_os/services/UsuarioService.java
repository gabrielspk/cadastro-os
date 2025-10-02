package com.github.gabrielspk.cadastro_os.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioDTO;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.exceptions.DatabaseException;
import com.github.gabrielspk.cadastro_os.exceptions.ResourceNotFoundException;
import com.github.gabrielspk.cadastro_os.mappers.UsuarioMapper;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioMapper mapper;
	
    public List<UsuarioDTO> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }
	
    public UsuarioDTO findById(Long id) {
        Usuario usuario = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado. Id: " + id));
        return mapper.toDTO(usuario);
    }
	
    public UsuarioDTO insert(UsuarioCreateDTO dto) {
        Usuario usuario = mapper.fromCreateDTO(dto);
        Usuario usuarioSalvo = repository.save(usuario);
        return mapper.toDTO(usuarioSalvo);
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
