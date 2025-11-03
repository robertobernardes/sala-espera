package br.gov.ba.sesab.sala_espera.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ba.sesab.sala_espera.domains.Estado;
import br.gov.ba.sesab.sala_espera.domains.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
	
	List<Municipio> findByEstado(Estado estado);
	
}
