package saude.api.entity.transporte;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import saude.api.dto.transporteDto.MotoristaDto;
import saude.api.dto.transporteDto.VeiculoDto;
import saude.api.entity.Usuario;
import saude.api.entity.transporte.AgendaPost.AgendarPost;

@Document
public class Agenda implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	private String id;
	@NotNull
	private String nomeAgenda;
	@NotNull
	private VeiculoDto veiculo;
	@NotNull
	private MotoristaDto motorista;
	@NotNull
	private List<LocalEspera> locaisEspera = new ArrayList<>();
	private String Obs;
	@NotNull
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate data;
	private int vagasOcupadas = 0;
	@Transient //transit é utilizado para informar ao spring que não precisa persistir esse mapeamento
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate dataInicio;
	@Transient
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate dataFinal;
	private List<String> diasDaSemana = new ArrayList<>();
    private String usuarioCriouAgenda;

	
	
	
   
	
	
	
	
	//construtor usado na criação dos dias da agenda
	public Agenda(Agenda a) {
		this.id = a.getId();
		this.nomeAgenda = a.getNomeAgenda();
		this.veiculo = a.getVeiculo();
		this.motorista = a.getMotorista();
		this.locaisEspera = a.getLocaisEspera();
		Obs = a.getObs();
		this.data = a.getData();
		this.vagasOcupadas = a.getVagasOcupadas();
		this.dataInicio = a.getDataInicio();
		this.dataFinal = a.getDataFinal();
		this.diasDaSemana = a.getDiasDaSemana();
		
	}
	
	

	
	
	
	
	public Agenda() {
		
	}
	
	
	
	

	
	
	
	// ------------------------ gets sets ------------------------------
	

	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public VeiculoDto getVeiculo() {
		return veiculo;
	}


     




	public void setVeiculo(VeiculoDto veiculo) {
		this.veiculo = veiculo;
	}







	public MotoristaDto getMotorista() {
		return motorista;
	}







	public void setMotorista(MotoristaDto motorista) {
		this.motorista = motorista;
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

	public List<LocalEspera> getLocaisEspera() {
		return locaisEspera;
	}
	public void setLocaisEspera(List<LocalEspera> locaisEspera) {
		this.locaisEspera = locaisEspera;
	}
	public String getObs() {
		return Obs;
	}
	public void setObs(String obs) {
		Obs = obs;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getUsuarioCriouAgenda() {
		return usuarioCriouAgenda;
	}

    public void setUsuarioCriouAgenda(String usuarioCriouAgenda) {
		this.usuarioCriouAgenda = usuarioCriouAgenda;
	}

	public List<String> getDiasDaSemana() {
		return diasDaSemana;
	}
	public void setDiasDaSemana(List<String> diasDaSemana) {
		this.diasDaSemana = diasDaSemana;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
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
		Agenda other = (Agenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
    
	
	

}
