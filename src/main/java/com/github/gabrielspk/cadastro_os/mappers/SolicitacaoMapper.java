package com.github.gabrielspk.cadastro_os.mappers;

import org.mapstruct.Mapper;

import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoDTO;
import com.github.gabrielspk.cadastro_os.entities.Solicitacao;

@Mapper(componentModel = "spring")
public interface SolicitacaoMapper {
	SolicitacaoDTO toDTO(Solicitacao usuario);
	Solicitacao fromCreateDTO(SolicitacaoCreateDTO dto);
}