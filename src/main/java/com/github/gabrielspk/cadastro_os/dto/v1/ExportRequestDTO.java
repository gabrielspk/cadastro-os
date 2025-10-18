package com.github.gabrielspk.cadastro_os.dto.v1;

import java.util.List;

public class ExportRequestDTO {
    private List<DocumentMatchDTO> documentosEncontrados;
    private List<String> documentosNaoEncontrados;
    private int qtdeArquivosPesquisados;
    private String nomeArquivo;
    
	public ExportRequestDTO() {
	}
	
	public ExportRequestDTO(List<DocumentMatchDTO> documentosEncontrados, List<String> documentosNaoEncontrados,
			int qtdeArquivosPesquisados, String nomeArquivo) {
		this.documentosEncontrados = documentosEncontrados;
		this.documentosNaoEncontrados = documentosNaoEncontrados;
		this.qtdeArquivosPesquisados = qtdeArquivosPesquisados;
		this.nomeArquivo = nomeArquivo;
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
	public int getQtdeArquivosPesquisados() {
		return qtdeArquivosPesquisados;
	}
	public void setQtdeArquivosPesquisados(int qtdeArquivosPesquisados) {
		this.qtdeArquivosPesquisados = qtdeArquivosPesquisados;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
    
}
