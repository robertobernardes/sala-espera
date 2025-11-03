package br.gov.ba.sesab.sala_espera.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.Paciente;
import br.gov.ba.sesab.sala_espera.repositories.PacienteRepository;

@Service
public class PacienteService {
    
	@Autowired
    private PacienteRepository repository;

    public List<Paciente> buscarTodos() {
        return repository.findAll();
    }
    
    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
    
    public Paciente buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }	
}