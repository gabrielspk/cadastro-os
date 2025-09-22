package com.github.gabrielspk.cadastro_os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielspk.cadastro_os.entities.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
	
}
