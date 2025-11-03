package br.gov.ba.sesab.sala_espera.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.domains.UnidadeSaude;
import br.gov.ba.sesab.sala_espera.services.SalaService;
import br.gov.ba.sesab.sala_espera.services.UnidadeSaudeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;

@Component
@ViewScoped
public class SalaBean implements Serializable {

	@Autowired
    private SalaService salaService;
	@Autowired 
    private UnidadeSaudeService unidadeSaudeService;

    private List<Sala> salas;
    private Sala salaSelecionada;
    
    private List<UnidadeSaude> unidadesDisponiveis;

    @PostConstruct
    public void init() {
        this.salas = salaService.buscarTodosByCriteria();
        this.unidadesDisponiveis = unidadeSaudeService.buscarTodos();
        this.salaSelecionada = new Sala();
    }
    
    public void carregarSalas() {
        this.salas = salaService.buscarTodosByCriteria();
    }

    public void salvarSala() {
    	try {
    		if (salaSelecionada.getDataCadastro() == null)
    			salaSelecionada.setDataCadastro(LocalDateTime.now());
    		
    		salaService.salvar(salaSelecionada);
	        carregarSalas();
	        this.salaSelecionada = null;	        
	        	        
	        // Adiciona a mensagem ao contexto JSF
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sala salva com sucesso!"));	        
	        // Atualiza apenas o componente de mensagens via AJAX
	        PrimeFaces.current().ajax().update("formPrincipal:messages");
	        PrimeFaces.current().executeScript("PF('salaDialog').hide()");
	    } catch (Exception e) {
	        e.printStackTrace();
	        PrimeFaces.current().executeScript("PF('messages').show({severity:'error', summary:'Erro', detail:'Erro ao salvar: " + e.getMessage() + "'})");
	    }
    }
    
    public void excluirSala() {
    	try {
	        if (salaSelecionada != null) {
	        	if (salaSelecionada.getUnidadeSaude().getId() != null) {
	        		// Adiciona a mensagem ao contexto JSF
	    	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error - Detalhe: ", "Não foi possível excluir a Sala. Existem reservas associadas a ela (Violação de Chave Estrangeira)"));	        
	    	        // Atualiza apenas o componente de mensagens via AJAX
	    	        PrimeFaces.current().ajax().update("formPrincipal:messages");
	    	        return;
	        	}
	            salaService.deletar(salaSelecionada.getId());
	            salas.remove(salaSelecionada);
	            this.salaSelecionada = null;
	            // Adiciona a mensagem ao contexto JSF
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sala excluída com sucesso!"));	        
		        // Atualiza apenas o componente de mensagens via AJAX
		        PrimeFaces.current().ajax().update("formPrincipal:messages");
	        }
	        PrimeFaces.current().executeScript("PF('deleteSalaDialog').hide()");
    	} catch (Exception e) {
    		e.printStackTrace();
	        PrimeFaces.current().executeScript("PF('messages').show({severity:'error', summary:'Erro', detail:'Erro ao salvar: " + e.getMessage() + "'})");
	    }
    }

    public void novaSalaDialog() {
        this.salaSelecionada = new Sala();
        PrimeFaces.current().executeScript("PF('salaDialog').show()");
    }
    
    public void prepararEdicao(Sala sala) {
        this.salaSelecionada = sala;
        PrimeFaces.current().executeScript("PF('salaDialog').show()");
    }
    
    public List<UnidadeSaude> getUnidadesDisponiveis() { 
        return unidadesDisponiveis; 
    }
    
    public void setUnidadesDisponiveis(List<UnidadeSaude> unidadesDisponiveis) {
        this.unidadesDisponiveis = unidadesDisponiveis;
    }

    public List<Sala> getSalas() { return salas; }
    public void setSalas(List<Sala> salas) { this.salas = salas; }
    public Sala getSalaSelecionada() { return salaSelecionada; }
    public void setSalaSelecionada(Sala salaSelecionada) { this.salaSelecionada = salaSelecionada; }
    
    public class IntegridadeDeDadosException extends RuntimeException {
        public IntegridadeDeDadosException(String message) {
            super(message);
        }
        public IntegridadeDeDadosException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
