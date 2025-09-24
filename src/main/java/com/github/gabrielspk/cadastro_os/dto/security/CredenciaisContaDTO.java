package com.github.gabrielspk.cadastro_os.dto.security;

import java.io.Serializable;
import java.util.Objects;

public class CredenciaisContaDTO implements Serializable {
	
	private String email;
	public String senha;
	
	public CredenciaisContaDTO() {}

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

	@Override
	public int hashCode() {
		return Objects.hash(email, senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CredenciaisContaDTO other = (CredenciaisContaDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(senha, other.senha);
	}
	
}
