package saude.api.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String id;
	@NotNull
	@Indexed
	private String usuario;
	@NotNull
	@Indexed
	private String nome;
	@NotNull
	private String senha;
	
	@DBRef(lazy=true)
    @NotNull
	private Ubs ubs;
	private String profile;
	
	
	
	
	

	public Usuario() {
		this.profile = "ROLE_USUARIO";
	}
	
	
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonIgnore
	public String getSenha() {
		return senha;
	}

	@JsonProperty
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Ubs getUbs() {
		return ubs;
	}

	public void setUbs(Ubs ubs) {
		this.ubs = ubs;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	
	
	
}
