package br.gov.ba.sesab.sala_espera.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.UnidadeSaude;
import br.gov.ba.sesab.sala_espera.repositories.UnidadeSaudeRepository;

@Service
public class UnidadeSaudeService {
    
	@Autowired
    private UnidadeSaudeRepository repository;

    public List<UnidadeSaude> buscarTodos() {
        return repository.findAll();
    }
    
    public UnidadeSaude salvar(UnidadeSaude unidade) {
        return repository.save(unidade);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
    
    public UnidadeSaude buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }	
}