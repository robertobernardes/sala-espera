package br.gov.ba.sesab.sala_espera.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.Estado;
import br.gov.ba.sesab.sala_espera.repositories.EstadoRepository;

@Service
public class EstadoService {
    
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> buscarTodos() {
        return estadoRepository.findAll();
    }
        
    public Estado buscarPorId(Integer id) {
        return estadoRepository.findById(id).orElse(null);
    }
}    
