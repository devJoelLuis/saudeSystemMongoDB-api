package saude.api.entity.transporte;

import java.io.Serializable;
import java.time.LocalDateTime;

import saude.api.entity.Usuario;
import saude.api.entity.transporte.AgendaPost.AgendarPost;

public class BancoVeiculo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int vagasOcupadas; //quantas vagas esse paciente irá ocupar, caso tenha um acompanhante será duas vagas
	private String nomePaciente;
	private String nomeAcompanhante;
	private int codigoAgendamento;
	private String localEspera;
	private String localAtendimento;
	private String agendador; // atendente que agendou o cidadao
	private String idUsuarioAgendador;
	private LocalDateTime dataHoraAgendamento; //data e hora do agendamento
	private String Obs;
	
	
	
	public BancoVeiculo() {
		this.vagasOcupadas = 0;
		this.codigoAgendamento = 0;
		this.nomeAcompanhante ="";
		this.nomePaciente = "";
		this.agendador = "";
		this.idUsuarioAgendador = "";
		this.Obs = "";
		this.localEspera = "";
		this.localAtendimento ="";
	}
	
	
	
	
	//metodo que recebe um AgendarPost 
	public void agendarPost(AgendarPost ag, int codigoAgendamento, Usuario user) {
	    this.nomePaciente = ag.getNomePaciente();
	    this.codigoAgendamento = codigoAgendamento;
	    this.nomeAcompanhante = ag.getNomeAcompanhante();
	    this.vagasOcupadas = this.getVagasOcupadas();
	    this.Obs = ag.getObs();
	    this.agendador = user.getNome();
	    this.idUsuarioAgendador = user.getId();
	    this.dataHoraAgendamento = LocalDateTime.now();
	    this.localEspera = ag.getLocalEspera();
	    this.localAtendimento = ag.getLocalAtendimento();
	}
	
	
	
	
	
	
	
//---------------------------- metodos get e set	
	



	public String getAgendador() {
		return agendador;
	}



	public int getVagasOcupadas() {
		return vagasOcupadas;
	}



 
 


	public String getLocalAtendimento() {
		return localAtendimento;
	}




	public void setLocalAtendimento(String localAtendimento) {
		this.localAtendimento = localAtendimento;
	}




	public int getCodigoAgendamento() {
		return codigoAgendamento;
	}




	public void setCodigoAgendamento(int codigoAgendamento) {
		this.codigoAgendamento = codigoAgendamento;
	}




	public void setVagasOcupadas(int vagasOcupadas) {
		this.vagasOcupadas = vagasOcupadas;
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







	public String getObs() {
		return Obs;
	}







	public void setObs(String obs) {
		Obs = obs;
	}







	public void setAgendador(String agendador) {
		this.agendador = agendador;
	}



	public String getIdUsuarioAgendador() {
		return idUsuarioAgendador;
	}



	public void setIdUsuarioAgendador(String idUsuarioAgendador) {
		this.idUsuarioAgendador = idUsuarioAgendador;
	}



	public LocalDateTime getDataHoraAgendamento() {
		return dataHoraAgendamento;
	}



	public void setDataHoraAgendamento(LocalDateTime dataHoraAgendamento) {
		this.dataHoraAgendamento = dataHoraAgendamento;
	}




	public String getLocalEspera() {
		return localEspera;
	}




	public void setLocalEspera(String localEspera) {
		this.localEspera = localEspera;
	}
	
	
	
	
	
	

}
