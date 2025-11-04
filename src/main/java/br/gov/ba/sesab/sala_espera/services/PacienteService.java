package br.gov.ba.sesab.sala_espera.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.Paciente;
import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.repositories.PacienteRepository;
import br.gov.ba.sesab.sala_espera.repositories.PacienteRepositoryJpa;

@Service
public class PacienteService {
    
	@Autowired
    private PacienteRepository pacienteRepository;
	@Autowired
    private PacienteRepositoryJpa pacienteRepositoryJpa;

    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }
    
    public List<Paciente> buscarTodosByCriteria() {
        return pacienteRepositoryJpa.findAllByCriteria();
    }
    
    public Paciente salvar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void deletar(Integer id) {
    	pacienteRepository.deleteById(id);
    }
    
    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findById(id).orElse(null);
    }	
}