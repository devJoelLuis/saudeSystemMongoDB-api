package saude.api.dto.transporteDto;

import java.io.Serializable;
import java.time.LocalDate;

import saude.api.entity.Cidadao;

public class CidadaoTransporteDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	
	private String id;
	private Integer numeroProntuario;
	private String cartaocid;
	private String nome;
	private LocalDate nascimento;
	private String rgNumero;
	private String cpf;
	private String cartaosus;
	private String telResidencial;
	private String telCelular;
	private String email;
	
	
	public CidadaoTransporteDto(Cidadao cid) {
		this.id = cid.getId();
		this.nome = cid.getNome();
		this.cartaocid = cid.getCartaocid();
		this.numeroProntuario = cid.getNumeroProntuario();
		this.nascimento = cid.getNascimento();
		this.rgNumero = cid.getRgNumero();
		this.cpf = cid.getCpf();
		this.cartaosus = cid.getCartaosus();
		this.telResidencial = cid.getTelResidencial();
		this.telCelular = cid.getTelCelular();
		this.email = cid.getEmail();
	}

	
	
	
	
	
	
	
	public CidadaoTransporteDto() {
	
	}
	
	
	
	
	
	
	
	








	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getNumeroProntuario() {
		return numeroProntuario;
	}
	public void setNumeroProntuario(Integer numeroProntuario) {
		this.numeroProntuario = numeroProntuario;
	}
	public String getCartaocid() {
		return cartaocid;
	}
	public void setCartaocid(String cartaocid) {
		this.cartaocid = cartaocid;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getNascimento() {
		return nascimento;
	}
	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}
	public String getRgNumero() {
		return rgNumero;
	}
	public void setRgNumero(String rgNumero) {
		this.rgNumero = rgNumero;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		CidadaoTransporteDto other = (CidadaoTransporteDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
}

