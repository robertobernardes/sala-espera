package br.gov.ba.sesab.sala_espera.domains;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.gov.ba.sesab.sala_espera.domains.Enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva_sala")
public class ReservaSala implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_sala")
	private Sala sala;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "dt_inicio")
	private LocalDateTime dataInicio;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "dt_final")
	private LocalDateTime dataFinal;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "dt_cadastro")
	private LocalDateTime dataCadastro;
		
	private Integer status;	
	private String statusReservaSala;
	
	public ReservaSala() {
		super();
	}

	public ReservaSala(Usuario usuario, Sala sala, LocalDateTime dataInicio, LocalDateTime dataFinal,
			LocalDateTime dataCadastro, Integer status) {
		super();
		this.usuario = usuario;
		this.sala = sala;
		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.dataCadastro = dataCadastro;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusReservaSala() {
		
		StatusEnum searchStatusEnum = StatusEnum.valueOf(this.getStatus());
        /*
		switch (searchStatusEnum) {
            case CADASTRADA:
                return searchStatusEnum.getDescricao();
                break;
            case CANCELADA:
                obj = condicionanteService.createObjetoRegistroFiscalizacao(obj, objDto);
                break;
            case ARQUIVADA:
                obj = emergenciaService.createObjetoRegistroFiscalizacao(obj, objDto);
                break;
            default:
                throw new IllegalArgumentException(MensagemUtil.MSG_A57.getMsgDescricao());
        }
        */
        
		return searchStatusEnum.getDescricao();
	}

	public void setStatusReservaSala(String statusReservaSala) {
		this.statusReservaSala = statusReservaSala;
	}
	
}
