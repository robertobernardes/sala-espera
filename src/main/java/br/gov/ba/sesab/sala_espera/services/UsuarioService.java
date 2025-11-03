package br.gov.ba.sesab.sala_espera.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.Usuario;
import br.gov.ba.sesab.sala_espera.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
	@Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }
    
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
    	usuarioRepository.deleteById(id);
    }
    
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}