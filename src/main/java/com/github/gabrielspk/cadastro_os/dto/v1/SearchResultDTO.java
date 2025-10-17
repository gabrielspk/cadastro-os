package com.github.gabrielspk.cadastro_os.dto.v1;

import java.util.List;

public class SearchResultDTO {
    private int arquivosPesquisados;
    private List<DocumentMatchDTO> documentosEncontrados;
    private List<String> documentosNaoEncontrados;
    private String tempoExecucao;
    
	public SearchResultDTO() {
	}
	
	public SearchResultDTO(int arquivosPesquisados, List<DocumentMatchDTO> documentosEncontrados,
			List<String> documentosNaoEncontrados, String tempoExecucao) {
		this.arquivosPesquisados = arquivosPesquisados;
		this.documentosEncontrados = documentosEncontrados;
		this.documentosNaoEncontrados = documentosNaoEncontrados;
		this.tempoExecucao = tempoExecucao;
	}
	
	public int getArquivosPesquisados() {
		return arquivosPesquisados;
	}
	public void setArquivosPesquisados(int arquivosPesquisados) {
		this.arquivosPesquisados = arquivosPesquisados;
	}
	public List<DocumentMatchDTO> getDocumentosEncontrados() {
		return documentosEncontrados;
	}
	public void setDocumentosEncontrados(List<DocumentMatchDTO> documentosEncontrados) {
		this.documentosEncontrados = documentosEncontrados;
	}
	public List<String> getDocumentosNaoEncontrados() {
		return documentosNaoEncontrados;
	}
	public void setDocumentosNaoEncontrados(List<String> documentosNaoEncontrados) {
		this.documentosNaoEncontrados = documentosNaoEncontrados;
	}
	public String getTempoExecucao() {
		return tempoExecucao;
	}
	public void setTempoExecucao(String tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}
    
    
    
}