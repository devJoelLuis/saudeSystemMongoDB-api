package saude.api.dto.transporteDto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class FaltaTransporteDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String idcidadao;
	private String localDestino;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate data;
	public String getIdcidadao() {
		return idcidadao;
	}
	public void setIdcidadao(String idcidadao) {
		this.idcidadao = idcidadao;
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
