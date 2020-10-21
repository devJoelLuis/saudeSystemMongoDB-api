package saude.api.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class HistoricoUbs {

	@Id
	private String id;
	private String acao;
	private String iduser;
	private String nomeUser;
	private String idUbs;
	private String nomeUbs;
	private LocalDateTime dataHora;
	
	
	
	
	
	
	
	
	
	public HistoricoUbs(Usuario user, String acao, Ubs ubs ) {
		
		this.acao = acao;
		this.iduser = user.getId();
		this.nomeUser = user.getNome();
		this.idUbs = ubs.getId();
		this.nomeUbs = ubs.getNome();
		this.dataHora = LocalDateTime.now();
	}






	public HistoricoUbs() {
		
	}
	
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getIduser() {
		return iduser;
	}
	public void setIduser(String iduser) {
		this.iduser = iduser;
	}
	public String getNomeUser() {
		return nomeUser;
	}
	public void setNomeUser(String nomeUser) {
		this.nomeUser = nomeUser;
	}
	public String getIdUbs() {
		return idUbs;
	}
	public void setIdUbs(String idUbs) {
		this.idUbs = idUbs;
	}
	public String getNomeUbs() {
		return nomeUbs;
	}
	public void setNomeUbs(String nomeUbs) {
		this.nomeUbs = nomeUbs;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	
	
	
}
