package com.github.gabrielspk.cadastro_os.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.gabrielspk.cadastro_os.entities.enums.TipoUsuario;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String email;
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
	@OneToMany(mappedBy = "usuarioCriador")
	private List<Solicitacao> solicitacoes = new ArrayList<>();
	
	public Usuario() {
	}
	
	public Usuario(long id, String nome, String email, String senha, TipoUsuario tipoUsuario) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipoUsuario = tipoUsuario;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return id == other.id;
	}	
}
