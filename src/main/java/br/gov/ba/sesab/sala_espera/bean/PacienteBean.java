package br.gov.ba.sesab.sala_espera.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.Endereco;
import br.gov.ba.sesab.sala_espera.domains.Estado;
import br.gov.ba.sesab.sala_espera.domains.Municipio;
import br.gov.ba.sesab.sala_espera.domains.Paciente;
import br.gov.ba.sesab.sala_espera.services.EstadoService;
import br.gov.ba.sesab.sala_espera.services.MunicipioService;
import br.gov.ba.sesab.sala_espera.services.PacienteService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;

@Component
@ViewScoped
public class PacienteBean implements Serializable {

	@Autowired
    private PacienteService pacienteService;
	@Autowired
    private EstadoService estadoService;
	@Autowired
    private MunicipioService municipioService;
	

	private List<Paciente> pacientes;
    private Paciente pacienteSelecionado;
    
    private List<Estado> estados;
    private List<Municipio> municipiosFiltrados;
    
    @PostConstruct
    public void init() {
        this.pacientes = pacienteService.buscarTodos();
        this.carregarEstados();
        this.municipiosFiltrados = new ArrayList<>();
        this.pacienteSelecionado = new Paciente();
    }
    
    public void carregarPacientes() {
        this.pacientes = pacienteService.buscarTodos();
    }
    
    public void carregarEstados() {
    	this.estados = estadoService.buscarTodos();
    }
    
    public void carregarMunicipiosPorEstado() {
        Estado estado = this.pacienteSelecionado.getEndereco().getEstadoSelecionado();
        if (estado != null) {
            this.municipiosFiltrados = municipioService.findByEstado(estado); 
        } else {
            this.municipiosFiltrados = new ArrayList<>();
        }
        
        if (estado == null) {
            this.pacienteSelecionado.getEndereco().setMunicipio(null);
        }
    }

    public void salvarPaciente() {
    	try {
    		pacienteService.salvar(pacienteSelecionado);
	        carregarPacientes();
	        this.pacienteSelecionado = null;	        
	                    
            // Mensagens
	        // Adiciona a mensagem ao contexto JSF
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Paciente salvo com sucesso!"));	        
	        // Atualiza apenas o componente de mensagens via AJAX
	        PrimeFaces.current().ajax().update("formPrincipal:messages");
            PrimeFaces.current().executeScript("PF('pacienteDialog').hide()");
	    } catch (Exception e) {
	        e.printStackTrace();
            PrimeFaces.current().ajax().update("formPrincipal:messages");
	        PrimeFaces.current().executeScript("PF('messages').show({severity:'error', summary:'Erro', detail:'Erro ao salvar: " + e.getMessage() + "'})");
	    }
    }
    
    public void excluirPaciente() {
        if (pacienteSelecionado != null) {
        	pacienteService.deletar(pacienteSelecionado.getId());
        	pacientes.remove(pacienteSelecionado);
            this.pacienteSelecionado = null;
            // Adiciona a mensagem ao contexto JSF
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Paciente excluído com sucesso!"));	        
	        // Atualiza apenas o componente de mensagens via AJAX
	        PrimeFaces.current().ajax().update("formPrincipal:messages");
        }
        PrimeFaces.current().executeScript("PF('deletesalaDialog').hide()");
    }
    
    public void novoPacienteDialog() {
        this.pacienteSelecionado = new Paciente();
        this.pacienteSelecionado.setEndereco(new Endereco());
    }
    
    public void prepararEdicao(Paciente paciente) {
        this.pacienteSelecionado = paciente;
        
        if (paciente.getEndereco() != null && paciente.getEndereco().getMunicipio() != null) {
            Estado estadoDoMunicipio = paciente.getEndereco().getMunicipio().getEstado();
            this.pacienteSelecionado.getEndereco().setEstadoSelecionado(estadoDoMunicipio);
            
            if (estadoDoMunicipio != null) {
                this.municipiosFiltrados = municipioService.findByEstado(estadoDoMunicipio);
            }
        }
        PrimeFaces.current().executeScript("PF('pacienteDialog').show()");
    }
    
    public Date getMaxDateTime() {
        return new Date();        
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
    
    public List<Estado> getEstados() {
        return estados;
    }
    
    public List<Municipio> getMunicipiosFiltrados() {
        return municipiosFiltrados;
    }
    
    public List<Paciente> getPacientes() { return pacientes; }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = pacientes; }
    public Paciente getPacienteSelecionado() { return pacienteSelecionado; }
    public void setPacienteSelecionado(Paciente pacienteSelecionado) { this.pacienteSelecionado = pacienteSelecionado; }		
}
