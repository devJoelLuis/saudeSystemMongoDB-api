package saude.api.dto.transporteDto;

import java.io.Serializable;

import saude.api.entity.transporte.Veiculo;

public class VeiculoDto implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String veiculo;
	private String placa;
	private int lugares;
	
	
	
	
	
	
	
	
	
	
	public VeiculoDto() {
		
	}
	
	
	public VeiculoDto(Veiculo v) {
		this.id = v.getId();
		this.veiculo = v.getVeiculo();
		this.placa = v.getPlaca();
		this.lugares = v.getLugares();
	}
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
	
	
	
	

}
