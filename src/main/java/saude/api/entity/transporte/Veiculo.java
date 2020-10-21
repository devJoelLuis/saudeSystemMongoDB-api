package saude.api.entity.transporte;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import saude.api.dto.transporteDto.VeiculoDto;

@Document
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@Size(max=60)
	@NotNull
	private String veiculo;
	@Indexed(unique=true)
	private String placa;
	private int lugares;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Veiculo() {
	}








	public Veiculo(VeiculoDto vd) {
		this.id = vd.getId();
		this.veiculo = vd.getVeiculo();
		this.placa = vd.getPlaca();
		this.lugares = vd.getLugares();
	}
	
	
	
	
	
	
	
	
	// get e set
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public int getLugares() {
		return lugares;
	}
	public void setLugares(int lugares) {
		this.lugares = lugares;
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
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
