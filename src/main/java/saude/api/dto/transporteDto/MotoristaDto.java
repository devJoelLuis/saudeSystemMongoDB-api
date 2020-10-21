package saude.api.dto.transporteDto;

import java.io.Serializable;
import java.time.LocalDate;

import saude.api.entity.transporte.Motorista;

public class MotoristaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nomeMotorista;
	private LocalDate cartaVencimento;
	private String telefone;
	private String cartaCategoria;
	
	
	
	
	
	
	
	
	
	
	public MotoristaDto() {
	}



	public MotoristaDto(Motorista m) {
		this.id = m.getId();
		this.nomeMotorista = m.getNomeMotorista();
		this.cartaVencimento = m.getCartaVencimento();
		this.telefone = m.getTelefone();
		this.cartaCategoria = m.getCartaCategoria();
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomeMotorista() {
		return nomeMotorista;
	}
	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}
	public LocalDate getCartaVencimento() {
		return cartaVencimento;
	}
	public void setCartaVencimento(LocalDate cartaVencimento) {
		this.cartaVencimento = cartaVencimento;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCartaCategoria() {
		return cartaCategoria;
	}
	public void setCartaCategoria(String cartaCategoria) {
		this.cartaCategoria = cartaCategoria;
	}
			
	
	

}
