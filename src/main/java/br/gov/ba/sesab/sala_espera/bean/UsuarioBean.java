package br.gov.ba.sesab.sala_espera.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.ReservaSala;
import br.gov.ba.sesab.sala_espera.domains.Usuario;
import br.gov.ba.sesab.sala_espera.services.ReservaSalaService;
import br.gov.ba.sesab.sala_espera.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;

@Component
@ViewScoped
public class UsuarioBean implements Serializable {
	
		@Autowired
	    private UsuarioService usuarioService;
		@Autowired
	    private ReservaSalaService reservaSalaService;
		
	    private List<Usuario> usuarios;
	    private Usuario usuarioSelecionado;
	    
	    @PostConstruct
	    public void init() {
	        this.usuarios = usuarioService.buscarTodos(); 
	    }
	    
	    public void carregarUsuarios() {
	        this.usuarios = usuarioService.buscarTodos();
	    }

	    public void salvarUsuario() {
	    	try {
	    		
	    		if (usuarioSelecionado.getDataCadastro() == null) 
	    			usuarioSelecionado.setDataCadastro(LocalDateTime.now());
	    		
	    		usuarioService.salvar(usuarioSelecionado);
		        carregarUsuarios();
		        this.usuarioSelecionado = null;	        
		        
		     // Adiciona a mensagem ao contexto JSF
		        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário salvo com sucesso!"));	        
		        // Atualiza apenas o componente de mensagens via AJAX
		        PrimeFaces.current().ajax().update("formPrincipal:messages");
		        PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
		    } catch (Exception e) {
		        e.printStackTrace();
	            PrimeFaces.current().ajax().update("formPrincipal:messages");
		        PrimeFaces.current().executeScript("PF('messages').show({severity:'error', summary:'Erro', detail:'Erro ao salvar: " + e.getMessage() + "'})");
		    }
	    }
	    
	    public void excluirUsuario() {
	        if (usuarioSelecionado != null) {
	        	//Pesquisando se usuário está associado alguma reserva de sala
	        	List<ReservaSala> reservaSalaResult = reservaSalaService.findByUsuario(usuarioSelecionado);        	
	        	if (!reservaSalaResult.isEmpty()) {
	        		// Adiciona a mensagem ao contexto JSF
	    	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error - Detalhe: ", "Não foi possível excluir o Usuário. Existem Reservas associadas a ele (Violação de Chave Estrangeira)"));	        
	    	        // Atualiza apenas o componente de mensagens via AJAX
	    	        PrimeFaces.current().ajax().update("formPrincipal:messages");
	    	        return;
	        	}	        	
	        	usuarioService.deletar(usuarioSelecionado.getId());
	            usuarios.remove(usuarioSelecionado);
	            this.usuarioSelecionado = null;
	        }
	        // Adiciona a mensagem ao contexto JSF
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário excluído com sucesso!"));	        
	        // Atualiza apenas o componente de mensagens via AJAX
	        PrimeFaces.current().ajax().update("formPrincipal:messages");
	        PrimeFaces.current().executeScript("PF('deletesalaDialog').hide()");
	    }
	    
	    public void novoUsuarioDialog() { 
	        this.usuarioSelecionado = new Usuario();
	    }
	    
	    public void prepararEdicao(Usuario usuario) {
	        this.usuarioSelecionado = usuario;
	    }
	    
	    public boolean filtroCpf(Object value, Object filter, Locale locale) {
	        if (filter == null || filter.toString().isEmpty()) {
	            return true;
	        }
	        if (value == null) {
	            return false;
	        }

	        String cpf = value.toString();
	        String filtro = filter.toString();
	        
	        // Remove mascara do campo
	        String cpfLimpo = cpf.replaceAll("[^0-9]", ""); 
	        
	        // Remove mascara do filtro
	        String filtroLimpo = filtro.replaceAll("[^0-9]", ""); 

	        // Compara o CPF limpo com o valor limpo do filtro
	        // O toUpperCase(locale) não é estritamente necessário para números, mas é bom para consistência.
	        return cpfLimpo.contains(filtroLimpo);
	    }

	    public List<Usuario> getUsuarios() { return usuarios; }
	    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
	    public Usuario getUsuarioSelecionado() { return usuarioSelecionado; }
	    public void setUsuarioSelecionado(Usuario usuarioSelecionado) { this.usuarioSelecionado = usuarioSelecionado; }
}
