package br.gov.ba.sesab.sala_espera.domains;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
	
	@Column(unique = true)
	private String nome;
	
	@Column(unique = true)
	private String uf;
	
	@Column(unique = true)
	private Integer codigo;
	
	@JoinColumn(name = "st_ativo")
	private boolean stAtivo;

	public Estado() {
		super();
	}

	public Estado(String nome, String uf, Integer codigo, boolean stAtivo) {
		super();
		this.nome = nome;
		this.uf = uf;
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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
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
		return Objects.hash(codigo, id, nome, stAtivo, uf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome)
				&& stAtivo == other.stAtivo && Objects.equals(uf, other.uf);
	}
}
