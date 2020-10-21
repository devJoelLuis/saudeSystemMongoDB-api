package saude.api.entity.transporte;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import saude.api.dto.transporteDto.MotoristaDto;

@Document
public class Motorista implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@Indexed(unique=true)
	@NotNull
	@Size(max=60)
	private String nomeMotorista;
	private LocalDate cartaVencimento;
	@Size(max=60)
	private String telefone;
	@Size(max=60)
	private String cartaCategoria;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Motorista() {
	}






	public Motorista(MotoristaDto md) {
		this.id = md.getId();
		this.nomeMotorista = md.getNomeMotorista();
		this.cartaVencimento = md.getCartaVencimento();
		this.telefone = md.getTelefone();
		this.cartaCategoria = md.getCartaCategoria();
	}
	
	
	
	
	
	
	//get e set
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
		Motorista other = (Motorista) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	

}
