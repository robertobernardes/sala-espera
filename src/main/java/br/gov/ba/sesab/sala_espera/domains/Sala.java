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
import jakarta.persistence.ManyToOne;

@Entity
public class Sala implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
	
	@Column(length=255)
	private String nome;
	
	private Integer capacidade;
	
	@ManyToOne
	@JoinColumn(name = "id_unidade_saude")
	private UnidadeSaude unidadeSaude;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "dt_cadastro")
	private LocalDateTime dataCadastro;
	
	@JoinColumn(name = "st_ativo")
	private boolean stAtivo;

	public Sala() {
		super();
	}

	public Sala(String nome, Integer capacidade, UnidadeSaude unidadeSaude, LocalDateTime dataCadastro, boolean stAtivo) {
		super();
		this.nome = nome;
		this.capacidade = capacidade;
		this.unidadeSaude = unidadeSaude;
		this.dataCadastro = dataCadastro;
		this.stAtivo = stAtivo;
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

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}	

	public UnidadeSaude getUnidadeSaude() {
		return unidadeSaude;
	}

	public void setUnidadeSaude(UnidadeSaude unidadeSaude) {
		this.unidadeSaude = unidadeSaude;
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
		return Objects.hash(capacidade, dataCadastro, id, nome, stAtivo, unidadeSaude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sala other = (Sala) obj;
		return Objects.equals(capacidade, other.capacidade) && Objects.equals(dataCadastro, other.dataCadastro)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome) && stAtivo == other.stAtivo
				&& Objects.equals(unidadeSaude, other.unidadeSaude);
	}
}
