package com.github.gabrielspk.cadastro_os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.gabrielspk.cadastro_os.dto.UsuarioCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.UsuarioDTO;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<Usuario> usuarioList = service.findAll();
		List<UsuarioDTO> userDtoList = usuarioList.stream().map(x -> new UsuarioDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(userDtoList);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
		Usuario usuario = service.findById(id);
		return ResponseEntity.ok().body(new UsuarioDTO(usuario));
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDTO> insert(@Valid @RequestBody UsuarioCreateDTO  usuarioDto){
		Usuario usuario = service.fromCreateDTO(usuarioDto);
		usuario = service.Insert(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new UsuarioDTO(usuario));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}