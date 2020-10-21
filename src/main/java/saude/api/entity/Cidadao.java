package saude.api.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import saude.api.dto.transporteDto.CidadaoDadosBasicosDto;
import saude.api.entity.transporte.FaltaTransporte;

@Document
public class Cidadao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private Integer idmysql;
	@Indexed
	@NotNull
    private Integer numeroProntuario;
	@Indexed
	@Size(min=1, max=30)
	private String cartaocid;
	@Indexed
	@NotNull
	@Size(min=2, max=80)
	private String nome;
	@NotNull
	private LocalDate nascimento;
	@Size(min=1, max=60)
    private String natCidade;
	@Size(min=1, max=60)
    private String natEstado;
	@Size(min=1, max=30)
    private String nacionalidade;
    @Indexed
    @Size(min=1, max=20)
    private String rgNumero;
    @Size(min=1, max=20)
    private String rgOrgao;
    @Size(min=1, max=60)
    private String rgEstado;
    @Indexed
    @Size(min=1, max=20)
    private String cpf;
    @Size(min=1, max=20)
    private String sexo;
    @Size(min=1, max=60)
    private String pai;
    @Size(min=1, max=60)
    private String mae;
    @Indexed
    @Size(min=0, max=60)
    private String cartaosus;
    @Size(min=0, max=60)
    private String telResidencial;
    @Size(min=0, max=60)
    private String telCelular;
    @Size(min=0, max=60)
    private String telComercial;
    @Size(min=0, max=60)
    private String telComlRamal;
    @Size(min=0, max=60)
    private String telRecado;
    private LocalDateTime dataCadastro;
    @Size(min=1, max=60)
    private String cadastrador;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;
    private String latitude;
    private String longitude;
    @Size(min=0, max=255)
    private String observacoes;
    private LocalDateTime ultimaAtualizacao;
    private Date dataBaixa;
    private String foto;
    @Size(min=1, max=60)
    private String observacaoCadastro;
    private String observacaoCidadao;
    private String suspenso;
    private String microArea;
    private String email;
    private List<LocalDate> datasDesbloqueio = new ArrayList<LocalDate>();
    private List<Falta> datasFaltas = new ArrayList<Falta>();
    private List<LocalDate> datasDesbloqueioTransporte = new ArrayList<LocalDate>();
    private List<FaltaTransporte> faltasTranporte = new ArrayList<FaltaTransporte>();
    @DBRef(lazy=true)
    @NotNull
    private Ubs ubs;
    private Integer bloquiado;
    private String motivoBloqueio;
    
    
    
    
    
    
    
    
    
    
    
    
    
	public Cidadao() {
		
	}







	public Cidadao(CidadaoDadosBasicosDto cb) {
	   this.nome = cb.getNome();
	   this.cartaosus = cb.getCartaosus();
	   this.nascimento = cb.getNascimento();
	   this.telCelular = cb.getTelCelular();
	   this.telResidencial = cb.getTelResidencial();
	}
	
	
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getIdmysql() {
		return idmysql;
	}
	public void setIdmysql(Integer idmysql) {
		this.idmysql = idmysql;
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
	
	
	public String getNacionalidade() {
		return nacionalidade;
	}
	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getPai() {
		return pai;
	}
	public void setPai(String pai) {
		this.pai = pai;
	}
	public String getMae() {
		return mae;
	}
	public void setMae(String mae) {
		this.mae = mae;
	}
	public String getCartaosus() {
		return cartaosus;
	}
	public void setCartaosus(String cartaosus) {
		this.cartaosus = cartaosus;
	}
	
	public String getCadastrador() {
		return cadastrador;
	}
	public void setCadastrador(String cadastrador) {
		this.cadastrador = cadastrador;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	
	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getSuspenso() {
		return suspenso;
	}
	public void setSuspenso(String suspenso) {
		this.suspenso = suspenso;
	}
	
	public String getMicroArea() {
		return microArea;
	}
	public void setMicroArea(String microArea) {
		this.microArea = microArea;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
		
	
	public Ubs getUbs() {
		return ubs;
	}
	public void setUbs(Ubs ubs) {
		this.ubs = ubs;
	}
	public Integer getBloquiado() {
		return bloquiado;
	}
	public void setBloquiado(Integer bloquiado) {
		this.bloquiado = bloquiado;
	}
	
	
	
	
	
	
	public LocalDate getNascimento() {
		return nascimento;
	}
	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public LocalDateTime getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}
	public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	public String getNatCidade() {
		return natCidade;
	}
	public void setNatCidade(String natCidade) {
		this.natCidade = natCidade;
	}
	public String getNatEstado() {
		return natEstado;
	}
	public void setNatEstado(String natEstado) {
		this.natEstado = natEstado;
	}
	public String getRgNumero() {
		return rgNumero;
	}
	public void setRgNumero(String rgNumero) {
		this.rgNumero = rgNumero;
	}
	public String getRgOrgao() {
		return rgOrgao;
	}
	public void setRgOrgao(String rgOrgao) {
		this.rgOrgao = rgOrgao;
	}
	public String getRgEstado() {
		return rgEstado;
	}
	public void setRgEstado(String rgEstado) {
		this.rgEstado = rgEstado;
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
	public String getTelComercial() {
		return telComercial;
	}
	public void setTelComercial(String telComercial) {
		this.telComercial = telComercial;
	}
	public String getTelComlRamal() {
		return telComlRamal;
	}
	public void setTelComlRamal(String telComlRamal) {
		this.telComlRamal = telComlRamal;
	}
	public String getTelRecado() {
		return telRecado;
	}
	public void setTelRecado(String telRecado) {
		this.telRecado = telRecado;
	}
	
	public Date getDataBaixa() {
		return dataBaixa;
	}
	public void setDataBaixa(Date dataBaixa) {
		this.dataBaixa = dataBaixa;
	}
	public Integer getNumeroProntuario() {
		return numeroProntuario;
	}
	public void setNumeroProntuario(Integer numeroProntuario) {
		this.numeroProntuario = numeroProntuario;
	}
	public String getObservacaoCadastro() {
		return observacaoCadastro;
	}
	public void setObservacaoCadastro(String observacaoCadastro) {
		this.observacaoCadastro = observacaoCadastro;
	}
	public String getObservacaoCidadao() {
		return observacaoCidadao;
	}
	public void setObservacaoCidadao(String observacaoCidadao) {
		this.observacaoCidadao = observacaoCidadao;
	}
	public List<Falta> getDatasFaltas() {
		
		return datasFaltas;
	}
	public void setDatasFaltas(List<Falta> datasFaltas) {
		this.datasFaltas = datasFaltas;
	}
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	
	
	
	
	public List<LocalDate> getDatasDesbloqueioTransporte() {
		return datasDesbloqueioTransporte;
	}
	public void setDatasDesbloqueioTransporte(List<LocalDate> datasDesbloqueioTransporte) {
		this.datasDesbloqueioTransporte = datasDesbloqueioTransporte;
	}
	public List<FaltaTransporte> getFaltasTranporte() {
		return faltasTranporte;
	}
	public void setFaltasTranporte(List<FaltaTransporte> faltasTranporte) {
		this.faltasTranporte = faltasTranporte;
	}
	public List<LocalDate> getDatasDesbloqueio() {
		return datasDesbloqueio;
	}
	public void setDatasDesbloqueio(List<LocalDate> datasDesbloqueio) {
		this.datasDesbloqueio = datasDesbloqueio;
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
		Cidadao other = (Cidadao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
    
    
    
    
    
    
}
