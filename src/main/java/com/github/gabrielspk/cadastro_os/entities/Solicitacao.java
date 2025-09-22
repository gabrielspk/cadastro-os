package com.github.gabrielspk.cadastro_os.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;

public class Solicitacao {
	
	private Long id;
	private String numeroSI;
	private String descricao;
	private LocalDateTime dataAbertura;
	private LocalDateTime dataFechamento;
	
	private StatusSolicitacao status;
	private Usuario usuarioCriador;
	
	public Solicitacao() {
		
	}

	public Solicitacao(Long id, String numeroSI, String descricao, LocalDateTime dataAbertura,
			LocalDateTime dataFechamento, StatusSolicitacao status, Usuario usuarioCriador) {
		this.id = id;
		this.numeroSI = numeroSI;
		this.descricao = descricao;
		this.dataAbertura = dataAbertura;
		this.dataFechamento = dataFechamento;
		this.status = status;
		this.usuarioCriador = usuarioCriador;
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

	public StatusSolicitacao getStatus() {
		return status;
	}

	public void setStatus(StatusSolicitacao status) {
		this.status = status;
	}

	public Usuario getUsuarioCriador() {
		return usuarioCriador;
	}

	public void setUsuarioCriador(Usuario usuarioCriador) {
		this.usuarioCriador = usuarioCriador;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solicitacao other = (Solicitacao) obj;
		return Objects.equals(id, other.id);
	}
	
}
