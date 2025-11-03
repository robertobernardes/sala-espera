package br.gov.ba.sesab.sala_espera.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.ba.sesab.sala_espera.domains.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer>{

}
