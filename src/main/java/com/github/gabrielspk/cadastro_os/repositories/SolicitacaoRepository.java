package com.github.gabrielspk.cadastro_os.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielspk.cadastro_os.entities.Solicitacao;
import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
	Optional<Solicitacao> findByNumeroSI(String numeroSI);
	
	Page<Solicitacao> findByStatus(StatusSolicitacao status, Pageable pageable);
	
	Page<Solicitacao> findByUsuarioCriador_Id(Long usuarioId, Pageable pageable);
	
	Page<Solicitacao> findByStatusAndUsuarioCriador_Id(StatusSolicitacao status, Long usuarioId, Pageable pageable);
}
