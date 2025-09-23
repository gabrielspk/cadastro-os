package com.github.gabrielspk.cadastro_os.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_solicitacao")
public class Solicitacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroSI;
	private String descricao;
	private LocalDateTime dataAbertura;
	private LocalDateTime dataFechamento;
	
	@Enumerated(EnumType.STRING)
	private StatusSolicitacao status;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuarioCriador;
	
	public Solicitacao() {
		
	}

	public Solicitacao(String numeroSI, String descricao, LocalDateTime dataAbertura,
			LocalDateTime dataFechamento, StatusSolicitacao status, Usuario usuarioCriador) {
		this.numeroSI = numeroSI;
		this.descricao = descricao;
		this.dataAbertura = dataAbertura;
		this.dataFechamento = dataFechamento;
		this.status = status;
		this.usuarioCriador = usuarioCriador;
	}
	
	public Solicitacao(String numeroSI, String descricao, Usuario usuarioCriador) {
	    this.numeroSI = numeroSI;
	    this.descricao = descricao;
	    this.usuarioCriador = usuarioCriador;
	    this.dataAbertura = LocalDateTime.now();
	    this.status = StatusSolicitacao.ABERTO;
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
