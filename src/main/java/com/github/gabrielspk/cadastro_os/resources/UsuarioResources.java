package com.github.gabrielspk.cadastro_os.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResources {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}