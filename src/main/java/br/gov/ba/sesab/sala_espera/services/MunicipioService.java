package br.gov.ba.sesab.sala_espera.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.Estado;
import br.gov.ba.sesab.sala_espera.domains.Municipio;
import br.gov.ba.sesab.sala_espera.repositories.MunicipioRepository;

@Service
public class MunicipioService {
    
	@Autowired
    private MunicipioRepository municipioRepository;
	
	
    public List<Municipio> buscarTodos() {
        return municipioRepository.findAll();
    }
        
    public Municipio buscarPorId(Integer id) {
        return municipioRepository.findById(id).orElse(null);
    }
    
    public List<Municipio> findByEstado(Estado estado) {
        // Chama o método do repositório para buscar municípios por estado
        return municipioRepository.findByEstado(estado);
    }
}