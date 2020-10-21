package saude.api.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class HistoricoUsuario {
   
	@Id
	private String id;
	private LocalDateTime dataHoraEvento;
	private String idUserAcao;
	private String nomeUsuarioAcao;
	private String acao;
	private String idUsuario;
	private String nomeUsuario;
	
	
	
	
	
	
	
	
	public HistoricoUsuario(Usuario u, String acao, Usuario userAcao) {
	
		this.dataHoraEvento = LocalDateTime.now();
		this.idUserAcao = userAcao.getId();
		this.nomeUsuario = userAcao.getNome();
		this.acao = acao;
		this.idUsuario = u.getId();
		this.nomeUsuario = u.getNome();
	}



	
	
	


	public HistoricoUsuario() {
		
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





	public String getIdUserAcao() {
		return idUserAcao;
	}





	public void setIdUserAcao(String idUserAcao) {
		this.idUserAcao = idUserAcao;
	}





	public String getNomeUsuarioAcao() {
		return nomeUsuarioAcao;
	}





	public void setNomeUsuarioAcao(String nomeUsuarioAcao) {
		this.nomeUsuarioAcao = nomeUsuarioAcao;
	}





	public String getAcao() {
		return acao;
	}





	public void setAcao(String acao) {
		this.acao = acao;
	}





	public String getIdUsuario() {
		return idUsuario;
	}





	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}





	public String getNomeUsuario() {
		return nomeUsuario;
	}





	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	
	
	
	
	
}
