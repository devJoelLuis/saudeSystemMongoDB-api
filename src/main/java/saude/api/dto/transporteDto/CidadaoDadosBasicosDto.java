package saude.api.dto.transporteDto;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class CidadaoDadosBasicosDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String nome;
	private String cartaosus;
	private String telResidencial;
	private String telCelular;
	@DateTimeFormat(pattern ="yyyy-MM-dd")
	private LocalDate nascimento;
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCartaosus() {
		return cartaosus;
	}
	public void setCartaosus(String cartaosus) {
		this.cartaosus = cartaosus;
	}
	public String getTelResidencial() {
		return telResidencial;
	}
	public void setTelResidencial(String telResidencial) {
		this.telResidencial = telResidencial;
	}
	public String getTelCelular() {
		return telCelular;
	}
	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}
	public LocalDate getNascimento() {
		return nascimento;
	}
	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}
	
	
	
	
	
	
	

}
