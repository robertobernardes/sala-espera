package br.gov.ba.sesab.sala_espera.domains;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Municipio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
		
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_estado")
	private Estado estado;
	
	@Column(unique = true)
	private Integer codigo;
	
	@JoinColumn(name = "st_ativo")
	private boolean stAtivo;

	public Municipio() {
		super();
	}

	public Municipio(String nome, Estado estado, Integer codigo, boolean stAtivo) {
		super();
		this.nome = nome;
		this.estado = estado;
		this.codigo = codigo;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public boolean isStAtivo() {
		return stAtivo;
	}

	public void setStAtivo(boolean stAtivo) {
		this.stAtivo = stAtivo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, id, estado, nome, stAtivo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Municipio other = (Municipio) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(id, other.id)
				&& Objects.equals(estado, other.estado) && Objects.equals(nome, other.nome)
				&& stAtivo == other.stAtivo;
	}	
}
