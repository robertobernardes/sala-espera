package br.gov.ba.sesab.sala_espera.domains;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
		
	@Column(length=255)
	private String logradouro;
	
	@Column(length=5)
	private String numero;
	
	@Column(length=8)
	private String cep;
	
	@Column(length=500)
	private String ponto_referencia;
	
	@Column(length=255)
	private String complemento;
	
	@Column(length=50)
	private String bairro;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_municipio")
	private Municipio municipio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "dt_cadastro")
	private LocalDateTime dataCadastro;
	
	@JoinColumn(name = "st_ativo")
	private boolean stAtivo;
	
	@Transient
	private Estado estadoSelecionado;

	public Endereco() {
		super();
	}

	public Endereco(String logradouro, String numero, String cep, String ponto_referencia, String complemento,
			String bairro, Municipio municipio, LocalDateTime dataCadastro, boolean stAtivo) {
		super();
		this.logradouro = logradouro;
		this.numero = numero;
		this.cep = cep;
		this.ponto_referencia = ponto_referencia;
		this.complemento = complemento;
		this.bairro = bairro;
		this.municipio = municipio;
		this.dataCadastro = dataCadastro;
		this.stAtivo = stAtivo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getPonto_referencia() {
		return ponto_referencia;
	}

	public void setPonto_referencia(String ponto_referencia) {
		this.ponto_referencia = ponto_referencia;
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
	
	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isStAtivo() {
		return stAtivo;
	}

	public void setStAtivo(boolean stAtivo) {
		this.stAtivo = stAtivo;
	}
	
	public Estado getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Estado estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, complemento, dataCadastro, id, municipio, logradouro, numero,
				ponto_referencia, stAtivo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(complemento, other.complemento) && Objects.equals(dataCadastro, other.dataCadastro)
				&& Objects.equals(id, other.id) && Objects.equals(municipio, other.municipio)
				&& Objects.equals(logradouro, other.logradouro) && Objects.equals(numero, other.numero)
				&& Objects.equals(ponto_referencia, other.ponto_referencia) && stAtivo == other.stAtivo;
	}
}
