package saude.api.entity.transporte;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import saude.api.dto.transporteDto.FaltaTransporteDto;

public class FaltaTransporte implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	
	private String localDestino;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate data;
	
	
	
	
	
	
	public FaltaTransporte() {
	}





	public FaltaTransporte(FaltaTransporteDto ft) {
		this.localDestino = ft.getLocalDestino();
		this.data = ft.getData();
	}
	
	
	
	
	
	public String getLocalDestino() {
		return localDestino;
	}
	public void setLocalDestino(String localDestino) {
		this.localDestino = localDestino;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	
	
	
	
	
}
