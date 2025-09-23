package com.github.gabrielspk.cadastro_os.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SolicitacaoUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numeroSI;
    private String descricao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

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
}