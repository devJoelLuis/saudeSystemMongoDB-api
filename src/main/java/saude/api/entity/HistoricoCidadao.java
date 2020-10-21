package saude.api.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class HistoricoCidadao {
   
	@Id
	private String id;
	private LocalDateTime dataHoraEvento;
	private String idUser;
	private String nomeUsuario;
	private String acao;
	private String idcidadao;
	private String nomeCidadao;
	
	
	
	
	
	
	
	
	public HistoricoCidadao(Cidadao c, String acao, Usuario u) {
	
		this.dataHoraEvento = LocalDateTime.now();
		this.idUser = u.getId();
		this.nomeUsuario = u.getNome();
		this.acao = acao;
		this.idcidadao = c.getId();
		this.nomeCidadao = c.getNome();
	}





	public HistoricoCidadao() {
		
	}
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDateTime getDataHoraEvento() {
		return dataHoraEvento;
	}
	public void setDataHoraEvento(LocalDateTime dataHoraEvento) {
		this.dataHoraEvento = dataHoraEvento;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getIdcidadao() {
		return idcidadao;
	}
	public void setIdcidadao(String idcidadao) {
		this.idcidadao = idcidadao;
	}
	public String getNomeCidadao() {
		return nomeCidadao;
	}
	public void setNomeCidadao(String nomeCidadao) {
		this.nomeCidadao = nomeCidadao;
	}
	
	
}
