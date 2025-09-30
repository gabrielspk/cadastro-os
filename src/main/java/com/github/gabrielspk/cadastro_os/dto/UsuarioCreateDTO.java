package com.github.gabrielspk.cadastro_os.dto;

import java.io.Serializable;

import com.github.gabrielspk.cadastro_os.entities.enums.TipoUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioCreateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@Email(message = "Email inválido")
	private String email;
	
	@NotBlank(message = "Senha é obrigatória")
	private String senha;
		
	public UsuarioCreateDTO() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
