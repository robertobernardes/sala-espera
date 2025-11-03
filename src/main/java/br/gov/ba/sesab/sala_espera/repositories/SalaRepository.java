package br.gov.ba.sesab.sala_espera.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.domains.UnidadeSaude;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {
	
	/**
     * Busca todas as salas que estão associadas à UnidadeSaude fornecida.
     * @param unidade A UnidadeSaude a ser filtrada.
     * @return Uma lista de objetos Sala pertencentes à UnidadeSaude.
     */
    List<Sala> findByUnidadeSaude(UnidadeSaude unidade);

}
