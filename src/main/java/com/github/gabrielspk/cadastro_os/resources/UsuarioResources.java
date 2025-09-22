package com.github.gabrielspk.cadastro_os.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.entities.enums.TipoUsuario;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResources {
	
	@GetMapping
	public ResponseEntity<Usuario> findAll() {
		Usuario usuario = new Usuario(1L, "Gabrel", "gabriel.ferreira@gmail.com", "999999", TipoUsuario.ADMIN);
		return ResponseEntity.ok().body(usuario);
	}
	
}
