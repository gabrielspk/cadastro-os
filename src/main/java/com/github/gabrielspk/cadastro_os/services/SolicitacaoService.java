package com.github.gabrielspk.cadastro_os.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoUpdateDTO;
import com.github.gabrielspk.cadastro_os.entities.Solicitacao;
import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;
import com.github.gabrielspk.cadastro_os.exceptions.DatabaseException;
import com.github.gabrielspk.cadastro_os.exceptions.ResourceNotFoundException;
import com.github.gabrielspk.cadastro_os.mappers.SolicitacaoMapper;
import com.github.gabrielspk.cadastro_os.repositories.SolicitacaoRepository;

@Service
public class SolicitacaoService {

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
    @Autowired
    private SolicitacaoMapper mapper;

    public Page<SolicitacaoDTO> findAll(StatusSolicitacao status, Long usuarioId, Pageable pageable) {
        Page<Solicitacao> solicitacoes;
        
        if (status != null && usuarioId != null) {
            solicitacoes = solicitacaoRepository.findByStatusAndUsuarioCriador_Id(status, usuarioId, pageable);
        } else if (status != null) {
            solicitacoes = solicitacaoRepository.findByStatus(status, pageable);
        } else if (usuarioId != null) {
            solicitacoes = solicitacaoRepository.findByUsuarioCriador_Id(usuarioId, pageable);
        } else {
            solicitacoes = solicitacaoRepository.findAll(pageable);
        }
        
        return solicitacoes.map(mapper::toDTO);
    }

	public SolicitacaoDTO findById(Long id) {
		Solicitacao solicitacao = solicitacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrado. Id: " + id));
		return mapper.toDTO(solicitacao);
	}

	public SolicitacaoDTO insert(SolicitacaoCreateDTO dto) {
		Solicitacao solicitacao = mapper.fromCreateDTO(dto);
		solicitacaoRepository.findByNumeroSI(solicitacao.getNumeroSI()).ifPresent(s -> {
			throw new DatabaseException("Número de SI já existe: " + solicitacao.getNumeroSI());
		});
		Solicitacao solicitacaoSalva = solicitacaoRepository.save(solicitacao);
		return mapper.toDTO(solicitacaoSalva);
	}

	public void delete(Long id) {
		if (!solicitacaoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Solicitacao não encontrada. Id " + id);
		}
		try {
			solicitacaoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public SolicitacaoDTO patch(Long id, SolicitacaoUpdateDTO dto) {
		Solicitacao solicitacao = solicitacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Solicitação não encontrada. Id: " + id));

		if (dto.getNumeroSI() != null) {
			solicitacaoRepository.findByNumeroSI(dto.getNumeroSI()).ifPresent(s -> {
				if (!s.getId().equals(solicitacao.getId())) {
					throw new DatabaseException("Número de SI já existe " + dto.getNumeroSI());
				}
			});
			solicitacao.setNumeroSI(dto.getNumeroSI());
		}
		if (dto.getDescricao() != null) {
			solicitacao.setDescricao(dto.getDescricao());
		}
		if (dto.getDataAbertura() != null) {
			solicitacao.setDataAbertura(dto.getDataAbertura());
		}
		if (dto.getDataFechamento() != null) {
			solicitacao.setDataFechamento(dto.getDataFechamento());
		}
		Solicitacao solicitacaoAtualizada = solicitacaoRepository.save(solicitacao);
		return mapper.toDTO(solicitacaoAtualizada);
	}
}
