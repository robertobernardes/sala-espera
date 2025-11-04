package br.gov.ba.sesab.sala_espera.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.ba.sesab.sala_espera.domains.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class PacienteRepositoryJpa {
	
	@Autowired
    private EntityManager entityManager;
	
	/**
     * Busca todas as entidades Paciente utilizando a Criteria API do JPA.
     * @return Lista de todos os Paciente.
     */
    public List<Paciente> findAllByCriteria() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Paciente> cq = cb.createQuery(Paciente.class);
        Root<Paciente> execPaciente = cq.from(Paciente.class);
        cq.select(execPaciente);
        cq.orderBy(cb.asc(execPaciente.get("nome")));
        return entityManager.createQuery(cq).getResultList();
    }
}
