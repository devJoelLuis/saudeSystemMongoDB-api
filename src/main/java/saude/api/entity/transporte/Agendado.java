package saude.api.entity.transporte;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Agendado implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private int codigoAgendamento;
	private String idagenda;
	private String nomeAgenda;
	private String idPaciente;
	private String nomePaciente;
	private LocalDate data;
	private int vagasOcupadas; //quantas vagas esse paciente irá ocupar, caso tenha um acompanhante será duas vagas
	private String nomeAcompanhante;
	private String localEspera;
	private String localAtendimento;
	private String agendador; // atendente que agendou o cidadao
	private String idUsuarioAgendador;
	private LocalDateTime dataHoraAgendamento; //data e hora do agendamento
	private String Obs;
	private String status; // status= ok, o agendamento está ativo, qualquer outro valor o agendamento estará cancelado

	
	
	
	
	
public Agendado() {
		this.status = "ok";
	}










	// -----------------get e set---------------------------------------------	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCodigoAgendamento() {
		return codigoAgendamento;
	}
	public void setCodigoAgendamento(int codigoAgendamento) {
		this.codigoAgendamento = codigoAgendamento;
	}

	public String getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(String idPaciente) {
		this.idPaciente = idPaciente;
	}
	public String getNomePaciente() {
		return nomePaciente;
	}
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getIdagenda() {
		return idagenda;
	}
	public void setIdagenda(String idagenda) {
		this.idagenda = idagenda;
	}
    public String getNomeAgenda() {
		return nomeAgenda;
	}
	public void setNomeAgenda(String nomeAgenda) {
		this.nomeAgenda = nomeAgenda;
	}
	public int getVagasOcupadas() {
		return vagasOcupadas;
	}

	public void setVagasOcupadas(int vagasOcupadas) {
		this.vagasOcupadas = vagasOcupadas;
	}

	public String getNomeAcompanhante() {
		return nomeAcompanhante;
	}
	public void setNomeAcompanhante(String nomeAcompanhante) {
		this.nomeAcompanhante = nomeAcompanhante;
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
	public String getAgendador() {
		return agendador;
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
	public String getObs() {
		return Obs;
	}
	public void setObs(String obs) {
		Obs = obs;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}










	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}










	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendado other = (Agendado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
}
