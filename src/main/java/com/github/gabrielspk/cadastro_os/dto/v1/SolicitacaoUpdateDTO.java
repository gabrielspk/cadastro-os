package com.github.gabrielspk.cadastro_os.dto.v1;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;

public class SolicitacaoUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numeroSI;
    private String descricao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private StatusSolicitacao status;

    public SolicitacaoUpdateDTO() {}

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
}