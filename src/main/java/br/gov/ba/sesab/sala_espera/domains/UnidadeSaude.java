package br.gov.ba.sesab.sala_espera.domains;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "unidade_saude")
public class UnidadeSaude implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
		
	@Column(length=255)
	private String nome;
	
	@Column(name = "razao_social", length=50)
	private String razaoSocial;
	
	@Column(length=150)
	private String sigla;
	
	@Column(length=14)
	private String cnpj;
	
	@Column(length=150)
	private String cnes;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "dt_cadastro")
	private LocalDateTime dataCadastro;
	
	@JoinColumn(name = "st_ativo")
	private boolean stAtivo;

	public UnidadeSaude() {
		super();
	}
	
	public UnidadeSaude(String nome) {
		super();
		this.nome = nome;
	}

	public UnidadeSaude(String nome, String razaoSocial, String sigla, String cnpj, String cnes,
			LocalDateTime dataCadastro, boolean stAtivo) {
		super();
		this.nome = nome;
		this.razaoSocial = razaoSocial;
		this.sigla = sigla;
		this.cnpj = cnpj;
		this.cnes = cnes;
		this.dataCadastro = dataCadastro;
		this.stAtivo = stAtivo;
	}
	
	@Override
    public UnidadeSaude clone() {
        return new UnidadeSaude(getNome(), getRazaoSocial(), getSigla(), getCnpj(), getCnes(), getDataCadastro(), isStAtivo());
    }
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCnes() {
		return cnes;
	}

	public void setCnes(String cnes) {
		this.cnes = cnes;
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

	@Override
	public int hashCode() {
		return Objects.hash(cnes, cnpj, dataCadastro, id, nome, razaoSocial, sigla, stAtivo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnidadeSaude other = (UnidadeSaude) obj;
		return Objects.equals(cnes, other.cnes) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(dataCadastro, other.dataCadastro) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(razaoSocial, other.razaoSocial)
				&& Objects.equals(sigla, other.sigla) && stAtivo == other.stAtivo;
	}
	
	
}
