package com.github.gabrielspk.cadastro_os.mappers;

import org.mapstruct.Mapper;

import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioDTO;
import com.github.gabrielspk.cadastro_os.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
	UsuarioDTO toDTO(Usuario usuario);
	Usuario fromCreateDTO(UsuarioCreateDTO dto);
}
