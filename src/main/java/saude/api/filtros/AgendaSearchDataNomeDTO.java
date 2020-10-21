package saude.api.filtros;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class AgendaSearchDataNomeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeAgenda;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInicio;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataFim;
	private int page;
	private int size;
	public String getNomeAgenda() {
		return nomeAgenda;
	}
	public void setNomeAgenda(String nomeAgenda) {
		this.nomeAgenda = nomeAgenda;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	
	

}
