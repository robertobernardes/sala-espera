package br.gov.ba.sesab.sala_espera.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.ReservaSala;
import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.domains.UnidadeSaude;
import br.gov.ba.sesab.sala_espera.services.SalaService;
import br.gov.ba.sesab.sala_espera.services.UnidadeSaudeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;

@Component
@ViewScoped
public class DisponibilidadeSalaBean implements Serializable {

	@Autowired
    private SalaService salaService;    
    @Autowired
    private UnidadeSaudeService unidadeSaudeService;
    
    private UnidadeSaude unidadeSaudeFiltro;
    private ReservaSala novaReserva;

    private LocalDateTime dataInicioFiltro;
    private LocalDateTime dataFimFiltro;
    
    // Resultados e Listas
    private List<Sala> salasDisponiveis;
    private List<UnidadeSaude> todasUnidades;

    
    @PostConstruct
    public void init() {
        todasUnidades = unidadeSaudeService.buscarTodos();
        // Inicializa as datas com o período atual como padrão
        dataInicioFiltro = LocalDateTime.now();
        dataFimFiltro = LocalDateTime.now().plusHours(1);
        
    }
    
    public void consultarDisponibilidade() {
        try {
            salasDisponiveis = salaService.listarSalasDisponiveis(
                unidadeSaudeFiltro,
                dataInicioFiltro,
                dataFimFiltro
            );
        } catch (Exception e) {
        	e.printStackTrace();
            PrimeFaces.current().ajax().update("formPrincipal:messages");
	        PrimeFaces.current().executeScript("PF('messages').show({severity:'error', summary:'Erro', detail:'Erro ao consultar: " + e.getMessage() + "'})");
        }
    }
    
    public String prepararReserva(Sala sala) {
    	novaReserva = new ReservaSala();

        novaReserva.setSala(sala);
        novaReserva.setDataInicio(dataInicioFiltro);
        novaReserva.setDataFinal(dataFimFiltro);
        
        return "reservaSala.xhtml?faces-redirect=true";
    }
    
    public List<Sala> getSalasDisponiveis() { return salasDisponiveis; }
    public UnidadeSaude getUnidadeSaudeFiltro() { return unidadeSaudeFiltro; }
    public void setUnidadeSaudeFiltro(UnidadeSaude unidadeSaudeFiltro) { this.unidadeSaudeFiltro = unidadeSaudeFiltro; }
    public LocalDateTime getDataInicioFiltro() { return dataInicioFiltro; }
    public void setDataInicioFiltro(LocalDateTime dataInicioFiltro) { this.dataInicioFiltro = dataInicioFiltro; }
    public LocalDateTime getDataFimFiltro() { return dataFimFiltro; }
    public void setDataFimFiltro(LocalDateTime dataFimFiltro) { this.dataFimFiltro = dataFimFiltro; }
    public List<UnidadeSaude> getTodasUnidades() { return todasUnidades; }		
}
