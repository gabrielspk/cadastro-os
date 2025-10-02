package com.github.gabrielspk.cadastro_os.dto.v1;

import java.io.Serializable;
import java.time.LocalDateTime;

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
	
    public SolicitacaoDTO() {
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
