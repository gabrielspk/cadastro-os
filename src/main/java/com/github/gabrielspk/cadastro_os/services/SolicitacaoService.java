package com.github.gabrielspk.cadastro_os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.SolicitacaoCreateDTO;
import com.github.gabrielspk.cadastro_os.entities.Solicitacao;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.repositories.SolicitacaoRepository;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;
import com.github.gabrielspk.cadastro_os.services.exceptions.DatabaseException;
import com.github.gabrielspk.cadastro_os.services.exceptions.ResourceNotFoundException;

@Service
public class SolicitacaoService {
	
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Solicitacao fromCreateDTO(SolicitacaoCreateDTO dto) {
	    Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
	            .orElseThrow(() -> new ResourceNotFoundException(dto.getUsuarioId()));

	    return new Solicitacao(dto.getNumeroSI(), dto.getDescricao(), usuario);
	}
	
	public List<Solicitacao> findAll() {
		return solicitacaoRepository.findAll();
	}
	
	public Solicitacao findById(Long id) {
		Optional<Solicitacao> solicitacao = solicitacaoRepository.findById(id);
		return solicitacao.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Solicitacao Insert(Solicitacao solicitacao) {
		solicitacaoRepository.findByNumeroSI(solicitacao.getNumeroSI()).ifPresent(s ->{
			throw new DatabaseException("Número de SI já existe: " + solicitacao.getNumeroSI());
		});
		
		return solicitacaoRepository.save(solicitacao);
	}
	
	public void delete(Long id) {
		if (!solicitacaoRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			solicitacaoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
