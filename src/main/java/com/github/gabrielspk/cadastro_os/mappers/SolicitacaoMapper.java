package com.github.gabrielspk.cadastro_os.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoDTO;
import com.github.gabrielspk.cadastro_os.entities.Solicitacao;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {
	
    @Mapping(target = "usuarioCriadorNome", source = "usuarioCriador.nome")
	SolicitacaoDTO toDTO(Solicitacao usuario);
    
	Solicitacao fromCreateDTO(SolicitacaoCreateDTO dto);
}