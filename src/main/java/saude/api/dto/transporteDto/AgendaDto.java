package saude.api.dto.transporteDto;

import java.io.Serializable;
import java.time.LocalDate;

import saude.api.entity.transporte.Agenda;

public class AgendaDto implements Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nomeAgenda;
	private String motorista;
	private LocalDate data;
	private int vagas;
	
	
	
	
	public AgendaDto(String id,String agenda, String motorista, LocalDate data, int vagas) {
		this.id = id;
		this.nomeAgenda = agenda;
		this.motorista = motorista;
		this.data = data;
		this.vagas = vagas;
	}

	
	



	public AgendaDto(Agenda a) {
		this.id = a.getId();
		this.nomeAgenda = a.getNomeAgenda();
		this.motorista = a.getMotorista().getNomeMotorista();
		this.data = a.getData();
		this.vagas = a.getVagas();
	}






	public AgendaDto() {
		
	}




	public String getId() {
		return id;
	}




	




	public void setId(String id) {
		this.id = id;
	}




	public String getMotorista() {
		return motorista;
	}




	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}




	public LocalDate getData() {
		return data;
	}




	public void setData(LocalDate data) {
		this.data = data;
	}
	
	



	public String getNomeAgenda() {
		return nomeAgenda;
	}




	public void setNomeAgenda(String agenda) {
		this.nomeAgenda = agenda;
	}




	public int getVagas() {
		return vagas;
	}




	public void setVagas(int vagas) {
		this.vagas = vagas;
	}




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//fecha classe
