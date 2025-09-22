package com.github.gabrielspk.cadastro_os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielspk.cadastro_os.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
}
