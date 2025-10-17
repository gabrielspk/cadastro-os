package com.github.gabrielspk.cadastro_os.dto.v1;

import java.util.List;

import lombok.Data;

@Data
public class SearchRequestDTO {
	
	private String diretorio;
	private boolean validarFpl;
	private List<String> documentos;
	
	public SearchRequestDTO() {
	}
	
	public SearchRequestDTO(String diretorio, boolean validarFpl, List<String> documentos) {
		this.diretorio = diretorio;
		this.validarFpl = validarFpl;
		this.documentos = documentos;
	}
	
	public String getDiretorio() {
		return diretorio;
	}
	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}
	public boolean isValidarFpl() {
		return validarFpl;
	}
	public void setValidarFpl(boolean validarFpl) {
		this.validarFpl = validarFpl;
	}
	public List<String> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<String> documentos) {
		this.documentos = documentos;
	}
}
