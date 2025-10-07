package com.github.gabrielspk.cadastro_os.dto.v1;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;

import jakarta.validation.constraints.NotBlank;

public class SolicitacaoCreateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "O número da SI é obrigatório")
    private String numeroSI;
	
	@NotBlank(message = "A descrição é obrigatória")
    private String descricao;
	
    private LocalDateTime dataAbertura; // opcional
    private LocalDateTime dataFechamento; // opcional
    private StatusSolicitacao status; // opcional
	
    public SolicitacaoCreateDTO() {
    	
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
}
