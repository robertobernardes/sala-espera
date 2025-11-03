package br.gov.ba.sesab.sala_espera.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.domains.UnidadeSaude;
import br.gov.ba.sesab.sala_espera.repositories.ReservaSalaRepository;
import br.gov.ba.sesab.sala_espera.repositories.SalaRepository;
import br.gov.ba.sesab.sala_espera.repositories.SalaRepositoryJpa;

@Service
public class SalaService {
    
	@Autowired
    private SalaRepository salaRepository;
	@Autowired
    private SalaRepositoryJpa salaRepositoryJpa;
	@Autowired
    private ReservaSalaRepository reservaSalaRepository;
    
	
    public List<Sala> buscarTodos() {
        return salaRepository.findAll();
    }
    
    public List<Sala> buscarTodosByCriteria() {
        return salaRepositoryJpa.findAllByCriteria();
    }
    
    public Sala salvar(Sala sala) {
        return salaRepository.save(sala);
    }

    public void deletar(Integer id) {
    	//salaRepository.deleteById(id);
    	salaRepositoryJpa.deletarPorIdCriteria(id);
    }    
    
    public Sala buscarPorId(Integer id) {
        return salaRepository.findById(id).orElse(null);
    }
    
    public List<Sala> findByUnidadeSaude(UnidadeSaude unidade) {
    	return salaRepository.findByUnidadeSaude(unidade);
    }
    
    /**
     * Retorna todas as salas disponíveis em uma Unidade de Saúde para um período específico.
     */
    public List<Sala> listarSalasDisponiveis(UnidadeSaude unidade, LocalDateTime inicio, LocalDateTime fim) {
        
        if (unidade == null || inicio == null || fim == null || inicio.isAfter(fim)) {
            return List.of(); 
        }
        // Consultando todas as salas da Unidade
        List<Sala> todasSalas = salaRepository.findByUnidadeSaude(unidade);

        // Consultando as salas RESERVADAS (ocupadas) no período
        List<Sala> salasReservadas = reservaSalaRepository.findSalasReservadasNoPeriodo(unidade, inicio, fim);

        // Diferença (TODAS - RESERVADAS = DISPONÍVEIS)
        List<Sala> salasDisponiveis = todasSalas.stream()
                .filter(sala -> !salasReservadas.contains(sala))
                .collect(Collectors.toList());
        return salasDisponiveis;
    }
}