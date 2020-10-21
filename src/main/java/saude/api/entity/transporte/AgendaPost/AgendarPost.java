package saude.api.entity.transporte.AgendaPost;

import java.io.Serializable;

public class AgendarPost implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String idagenda;
	private String localEspera;
	private String localAtendimento;
	private String idcidadao;
	private String nomePaciente;
	private String nomeAcompanhante;
	private int vagasOcupadas;
	private String obs;
	
	
	
	
	
	public String getIdagenda() {
		return idagenda;
	}
	public void setIdagenda(String idagenda) {
		this.idagenda = idagenda;
	}
	

	public String getLocalEspera() {
		return localEspera;
	}
	public void setLocalEspera(String localEspera) {
		this.localEspera = localEspera;
	}
	
	
	
	public String getLocalAtendimento() {
		return localAtendimento;
	}
	public void setLocalAtendimento(String localAtendimento) {
		this.localAtendimento = localAtendimento;
	}
	public String getIdcidadao() {
		return idcidadao;
	}
	public void setIdcidadao(String idcidadao) {
		this.idcidadao = idcidadao;
	}
	public String getNomePaciente() {
		return nomePaciente;
	}
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	public String getNomeAcompanhante() {
		return nomeAcompanhante;
	}
	public void setNomeAcompanhante(String nomeAcompanhante) {
		this.nomeAcompanhante = nomeAcompanhante;
	}
	
	public int getVagasOcupadas() {
		return vagasOcupadas;
	}
	public void setVagasOcupadas(int vagasOcupadas) {
		this.vagasOcupadas = vagasOcupadas;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
}
