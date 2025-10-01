package com.github.gabrielspk.cadastro_os.dto.v1;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SolicitacaoCreateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "O número da SI é obrigatório")
    private String numeroSI;
	
	@NotBlank(message = "A descrição é obrigatória")
    private String descricao;
	
	@NotNull(message = "O ID do usuário criador é obrigatório")
    private Long usuarioId;
	
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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
}
