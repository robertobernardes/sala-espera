package br.gov.ba.sesab.sala_espera.domains;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
			
	private String nome;
	
	@Column(name = "nome_social")
	private String nomeSocial;
	
	private Boolean sexo;
	
	@Column(name = "nome_mae")
	private String nomeMae;
	
	@Column(name = "nome_pai")
	private String nomePai;

	private String telefone;		
	private String email;
	private String cpf;
	private String rg;
	private String cns;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "dt_nascimento")
	private Date dataNascimento;
		
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;
	
	
	public Paciente() {
		super();
	}

	public Paciente(String nome, String nomeSocial, Boolean sexo, String nomeMae, String nomePai,
			String telefone, String email, String cpf, String rg, String cns, Date dataNascimento,
			Endereco endereco) {
		super();
		this.nome = nome;
		this.nomeSocial = nomeSocial;
		this.sexo = sexo;
		this.nomeMae = nomeMae;
		this.nomePai = nomePai;
		this.telefone = telefone;
		this.email = email;
		this.cpf = cpf;
		this.rg = rg;
		this.cns = cns;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
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

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}
	
	public Boolean getSexo() {
		return sexo;
	}

	public void setSexo(Boolean sexo) {
		this.sexo = sexo;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCns() {
		return cns;
	}

	public void setCns(String cns) {
		this.cns = cns;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cns, cpf, dataNascimento, email, endereco, id, nome, nomeMae, nomePai, nomeSocial, rg, sexo,
				telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return Objects.equals(cns, other.cns) && Objects.equals(cpf, other.cpf)
				&& Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(email, other.email)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(nomeMae, other.nomeMae)
				&& Objects.equals(nomePai, other.nomePai) && Objects.equals(nomeSocial, other.nomeSocial)
				&& Objects.equals(rg, other.rg) && sexo == other.sexo && Objects.equals(telefone, other.telefone);
	}
}
