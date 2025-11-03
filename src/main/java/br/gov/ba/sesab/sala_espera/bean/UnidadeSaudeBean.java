package br.gov.ba.sesab.sala_espera.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UnidadeSaudeBean implements Serializable {

	@Autowired
    private UnidadeSaudeService unidadeSaudeService;
	@Autowired
    private SalaService salaService;

    private List<UnidadeSaude> unidades;
    private UnidadeSaude unidadeSelecionada;

    @PostConstruct
    public void init() {
        this.unidades = unidadeSaudeService.buscarTodos();
    }
    
    public void carregarUnidades() {
        this.unidades = unidadeSaudeService.buscarTodos();
    }

    public void salvarUnidade() {
    	try {
    		if (unidadeSelecionada.getDataCadastro() == null)
    			unidadeSelecionada.setDataCadastro(LocalDateTime.now());
    		
    		unidadeSaudeService.salvar(unidadeSelecionada);
	        carregarUnidades();
	        this.unidadeSelecionada = null;
	        // Adiciona a mensagem ao contexto JSF
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Unidade de Saúde salva com sucesso!"));	        
	        // Atualiza apenas o componente de mensagens via AJAX
	        PrimeFaces.current().ajax().update("formPrincipal:messages");
	        PrimeFaces.current().executeScript("PF('unidadeDialog').hide()");
	    } catch (Exception e) {
	        e.printStackTrace();
	        PrimeFaces.current().executeScript("PF('messages').show({severity:'error', summary:'Erro', detail:'Erro ao salvar: " + e.getMessage() + "'})");
	    }
    }
    
    public void excluirUnidade() {
        if (unidadeSelecionada != null) {
        	//Pesquisando se a Unidade está associado alguma sala
        	List<Sala> salasResult = salaService.findByUnidadeSaude(unidadeSelecionada);
        	if (!salasResult.isEmpty()) {
        		// Adiciona a mensagem ao contexto JSF
    	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error - Detalhe: ", "Não foi possível excluir a Unidade de Saúde. Existem Salas associadas a ela (Violação de Chave Estrangeira)"));	        
    	        // Atualiza apenas o componente de mensagens via AJAX
    	        PrimeFaces.current().ajax().update("formPrincipal:messages");
    	        return;
        	}
            unidadeSaudeService.deletar(unidadeSelecionada.getId());
            unidades.remove(unidadeSelecionada);
            this.unidadeSelecionada = null;
            // Adiciona a mensagem ao contexto JSF
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Unidade de Saúde excluída com sucesso!"));	        
	        // Atualiza apenas o componente de mensagens via AJAX
	        PrimeFaces.current().ajax().update("formPrincipal:messages");
        }
        PrimeFaces.current().executeScript("PF('deleteUnidadeDialog').hide()");
    }
    
    // Método auxiliar para iniciar o dialog de criação
    public void novaUnidadeDialog() {
        this.unidadeSelecionada = new UnidadeSaude();
        PrimeFaces.current().executeScript("PF('unidadeDialog').show()");
    }
    
    // Método auxiliar para iniciar o dialog de edição
    public void prepararEdicao(UnidadeSaude unidade) {
        this.unidadeSelecionada = unidade;
        PrimeFaces.current().executeScript("PF('unidadeDialog').show()");
    }
    
    public boolean filtroCnpj(Object value, Object filter, Locale locale) {
        if (filter == null || filter.toString().isEmpty()) {
            return true;
        }
        if (value == null) {
            return false;
        }

        String cnpj = value.toString();
        String filtro = filter.toString();
        
        // Remove mascara do campo
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", ""); 
        
        // Remove mascara do filtro
        String filtroLimpo = filtro.replaceAll("[^0-9]", ""); 

        // Compara o CNPJ limpo com o valor limpo do filtro
        // O toUpperCase(locale) não é estritamente necessário para números, mas é bom para consistência.
        return cnpjLimpo.contains(filtroLimpo);
    }

    public List<UnidadeSaude> getUnidades() { return unidades; }
    public void setUnidades(List<UnidadeSaude> unidades) { this.unidades = unidades; }
    public UnidadeSaude getUnidadeSelecionada() { return unidadeSelecionada; }
    public void setUnidadeSelecionada(UnidadeSaude unidadeSelecionada) { this.unidadeSelecionada = unidadeSelecionada; }
}
