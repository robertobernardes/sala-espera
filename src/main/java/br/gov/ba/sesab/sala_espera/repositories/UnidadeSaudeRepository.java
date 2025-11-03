package br.gov.ba.sesab.sala_espera.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ba.sesab.sala_espera.domains.UnidadeSaude;

@Repository
public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, Integer>{

}
