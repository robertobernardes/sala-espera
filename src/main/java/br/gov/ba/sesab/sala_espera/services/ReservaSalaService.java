package br.gov.ba.sesab.sala_espera.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.ReservaSala;
import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.domains.Usuario;
import br.gov.ba.sesab.sala_espera.repositories.ReservaSalaRepository;

@Service
public class ReservaSalaService {
	
	@Autowired
    private ReservaSalaRepository reservaSalaRepository;

    public List<ReservaSala> buscarTodos() {
        return reservaSalaRepository.findAll();
    }
    
    public ReservaSala salvar(ReservaSala reservaSala) {
        return reservaSalaRepository.save(reservaSala);
    }

    public void deletar(Integer id) {
    	reservaSalaRepository.deleteById(id);
    }
    
    public ReservaSala buscarPorId(Integer id) {
        return reservaSalaRepository.findById(id).orElse(null);
    }
    
    public void editarStatus(Integer idStatus, Integer id) {
    	reservaSalaRepository.editarStatus(idStatus, id);
    }
    
    public boolean isSalaDisponivel(Integer sala, LocalDateTime inicio, LocalDateTime fim, Integer idReservaAtual) {
    	if (idReservaAtual == null)
    		idReservaAtual = 0;
    	long conflitos = reservaSalaRepository.contarReservasConflitantes(sala, inicio, fim, idReservaAtual);
        return conflitos == 0;
    }
    
    public List<ReservaSala> findByUsuario(Usuario usuario) {
    	return reservaSalaRepository.findByUsuario(usuario);
    }
    
    public List<ReservaSala> findBySala(Sala sala) {
    	return reservaSalaRepository.findBySala(sala);
    }
}
