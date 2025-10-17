package com.github.gabrielspk.cadastro_os.dto.v1;

public class DocumentMatchDTO {
    private String documento;
    private String arquivo;
    
    
    public DocumentMatchDTO() {
	}
	public DocumentMatchDTO(String documento, String arquivo) {
		this.documento = documento;
		this.arquivo = arquivo;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getArquivo() {
		return arquivo;
	}
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
    
    
    
}