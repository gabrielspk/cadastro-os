package com.github.gabrielspk.cadastro_os.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.github.gabrielspk.cadastro_os.entities.Solicitacao;

public class SolicitacaoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Long id;
    private String numeroSI;
    private String descricao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private String status;
    private Long usuarioCriadorId;
    private String usuarioCriadorNome;
	
    public SolicitacaoDTO(Solicitacao solicitacao) {
		this.id = solicitacao.getId();
		this.numeroSI = solicitacao.getNumeroSI();
		this.descricao = solicitacao.getDescricao();
		this.dataAbertura = solicitacao.getDataAbertura();
		this.dataFechamento = solicitacao.getDataFechamento();
		this.status = solicitacao.getStatus().name();
		this.usuarioCriadorId = solicitacao.getUsuarioCriador().getId();
		this.usuarioCriadorNome = solicitacao.getUsuarioCriador().getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroSI() {
		return numeroSI;
	}

	public void setNumeroSI(String numeroSI) {
		this.numeroSI = numeroSI;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDateTime getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDateTime dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUsuarioCriadorId() {
		return usuarioCriadorId;
	}

	public void setUsuarioCriadorId(Long usuarioCriadorId) {
		this.usuarioCriadorId = usuarioCriadorId;
	}

	public String getUsuarioCriadorNome() {
		return usuarioCriadorNome;
	}

	public void setUsuarioCriadorNome(String usuarioCriadorNome) {
		this.usuarioCriadorNome = usuarioCriadorNome;
	}
	
	
	
}
