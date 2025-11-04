package br.gov.ba.sesab.sala_espera.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.ReservaSala;
import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.domains.Usuario;
import br.gov.ba.sesab.sala_espera.domains.Enums.StatusEnum;
import br.gov.ba.sesab.sala_espera.services.ReservaSalaService;
import br.gov.ba.sesab.sala_espera.services.SalaService;
import br.gov.ba.sesab.sala_espera.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;

@Component
@ViewScoped
public class ReservaSalaBean implements Serializable {

	@Autowired
    private ReservaSalaService reservaSalaService;
	@Autowired
    private SalaService salaService;
	@Autowired
    private UsuarioService usuarioService;

    private List<ReservaSala> reservaSalas;
    private ReservaSala reservaSalaSelecionada;
    	
    private List<Sala> salasDisponiveis;
    private List<Usuario> usuariosDisponiveis;
    		
    @PostConstruct
    public void init() {
        this.reservaSalas = reservaSalaService.buscarTodos();    	
        this.salasDisponiveis = salaService.buscarTodos();
        this.usuariosDisponiveis = usuarioService.buscarTodos();
        this.reservaSalaSelecionada = new ReservaSala();
    }
    
    public void carregarReservaSalas() {
        this.reservaSalas = reservaSalaService.buscarTodos();
    }
    
    public boolean verificarDisponibilidadeReservaSalas() {
    	return reservaSalaService.isSalaDisponivel(
    			reservaSalaSelecionada.getSala().getId(), 
    			reservaSalaSelecionada.getDataInicio(), 
    			reservaSalaSelecionada.getDataFinal(), 
    			reservaSalaSelecionada.getId()
    		);
    }
    
    public void salvarReservaSala() {
        try {
        	
        	boolean disponivel = verificarDisponibilidadeReservaSalas();
        	if (!disponivel) {
        		FacesContext context = FacesContext.getCurrentInstance();
        		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Indisponibilidade", "A sala já está reservada no período selecionado."));
        		context.validationFailed();
        		return;
        	}    	
        	
        	if (reservaSalaSelecionada.getDataCadastro() == null)
        		reservaSalaSelecionada.setDataCadastro(LocalDateTime.now());
        	
        	reservaSalaSelecionada.setStatus(StatusEnum.CADASTRADA.getCodigo());
            reservaSalaService.salvar(reservaSalaSelecionada);
            carregarReservaSalas();
            this.reservaSalaSelecionada = null;    	
            
            PrimeFaces.current().executeScript("PF('reservaSalaDialog').hide()"); 
            // Adiciona a mensagem ao contexto JSF
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva de Sala salva com sucesso!"));	    	
	    	// Atualiza apenas o componente de mensagens via AJAX
	    	PrimeFaces.current().ajax().update("formPrincipal:messages");
        } catch (Exception e) {
            e.printStackTrace();
            PrimeFaces.current().ajax().update("formPrincipal:messages");
            PrimeFaces.current().executeScript("PF('messages').show({severity:'error', summary:'Erro', detail:'Erro ao salvar: " + e.getMessage() + "'})");
        }
    }
        
    public void excluirReservaSala() {
        if (reservaSalaSelecionada != null) {
            reservaSalaService.deletar(reservaSalaSelecionada.getId());
            reservaSalas.remove(reservaSalaSelecionada);
            this.reservaSalaSelecionada = null;
            // Adiciona a mensagem ao contexto JSF
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva de Sala excluída com sucesso!"));	    	
	    	// Atualiza apenas o componente de mensagens via AJAX
	    	PrimeFaces.current().ajax().update("formPrincipal:messages");
        }
        PrimeFaces.current().executeScript("PF('deleteReservaSalaDialog').hide()");
    }
    
    public void cancelarReservaSala() {
        if (reservaSalaSelecionada != null) {
            reservaSalaService.editarStatus(StatusEnum.CANCELADA.getCodigo(), reservaSalaSelecionada.getId());
            carregarReservaSalas();
            this.reservaSalaSelecionada = new ReservaSala();
            
            // Adiciona a mensagem ao contexto JSF
	    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Reserva de Sala cancelada com sucesso!"));	    	
	    	// Atualiza apenas o componente de mensagens via AJAX
	    	PrimeFaces.current().ajax().update("formPrincipal:messages");
        }
        PrimeFaces.current().executeScript("PF('cancelarReservaSalaDialog').hide()");
    }
    
    public void novaReservaSalaDialog() {
        this.reservaSalaSelecionada = new ReservaSala();
    }
    
    public void prepararEdicao(ReservaSala reservaSala) {
        this.reservaSalaSelecionada = reservaSala;
    }
    
    /**
     * Ajusta dataFinal para ser, no mínimo, igual a dataInicio.
     */
    public void ajustarDataFinal() {
        LocalDateTime inicio = reservaSalaSelecionada.getDataInicio();
        LocalDateTime fim = reservaSalaSelecionada.getDataFinal();

        if (inicio != null && fim != null && inicio.isAfter(fim)) {     
        	reservaSalaSelecionada.setDataFinal(inicio);
        }
    }
    
    /**
     * Retorna a data de início selecionada como Date para uso no mindate do dataFinal.
     * Retorna o MinDateTime global se for nulo.
     */
    public Date getDataInicioAsDate() {
        LocalDateTime inicio = reservaSalaSelecionada.getDataInicio();
        if (inicio == null) {
            // Se a data de início for nula, o limite mínimo deve ser o MinDateTime global.
            return getMinDateTime();
        }
        return Date.from(inicio.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converte dataFinal (LocalDateTime) para Date, adicionando 1 segundo (delta).
     * GARANTE que o valor retornado seja sempre maior que o MinDateTime global para 
     * satisfazer a validação mindate < maxdate do p:calendar dataInicio.
     */
    public Date getDataFinalAsDateWithDelta() {
        LocalDateTime fim = reservaSalaSelecionada.getDataFinal();
        
        LocalDateTime rawLimit;
        if (fim == null) {
            rawLimit = LocalDateTime.now().plus(1, ChronoUnit.YEARS);
        } else {
            rawLimit = fim.plusSeconds(1); 
        }
        
        LocalDateTime minDateTimeLDT = LocalDateTime.now().minusSeconds(5);
        
        if (!rawLimit.isAfter(minDateTimeLDT)) {
            rawLimit = minDateTimeLDT.plusSeconds(1);
        }

        return Date.from(rawLimit.atZone(ZoneId.systemDefault()).toInstant());
    }

    
    /**
     * Retorna o limite mínimo de data e hora permitido (o momento atual menos 5 segundos de buffer).
     * Este buffer é essencial para evitar o erro de validação por milissegundos.
     */
    public Date getMinDateTime() {
        // Cria um buffer de 5 segundos no passado
        return Date.from(LocalDateTime.now().minusSeconds(5).atZone(ZoneId.systemDefault()).toInstant());
    }
    
	public List<Sala> getSalasDisponiveis() {
		return salasDisponiveis;
	}

	public void setSalasDisponiveis(List<Sala> salasDisponiveis) {
		this.salasDisponiveis = salasDisponiveis;
	}

	public List<Usuario> getUsuariosDisponiveis() {
		return usuariosDisponiveis;
	}

	public void setUsuariosDisponiveis(List<Usuario> usuariosDisponiveis) {
		this.usuariosDisponiveis = usuariosDisponiveis;
	}

	public List<ReservaSala> getReservaSalas() { return reservaSalas; }
    public void setReservaSalas(List<ReservaSala> reservaSalas) { this.reservaSalas = reservaSalas; }
    public ReservaSala getReservaSalaSelecionada() { return reservaSalaSelecionada; }
    public void setReservaSalaSelecionada(ReservaSala reservaSalaSelecionada) { this.reservaSalaSelecionada = reservaSalaSelecionada; }
}