package br.gov.ba.sesab.sala_espera.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ba.sesab.sala_espera.domains.Sala;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class SalaRepositoryJpa {
	
	@Autowired
    private EntityManager entityManager;
	
	/**
     * Busca todas as entidades Sala utilizando a Criteria API do JPA.
     * @return Lista de todas as Salas.
     */
    public List<Sala> findAllByCriteria() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sala> cq = cb.createQuery(Sala.class);
        Root<Sala> execSala = cq.from(Sala.class);
        cq.select(execSala);
        cq.orderBy(cb.asc(execSala.get("nome")));
        return entityManager.createQuery(cq).getResultList();
    }
    
    @Transactional
    public void deletarPorIdCriteria(Integer id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Sala> cd = cb.createCriteriaDelete(Sala.class);
        Root<Sala> root = cd.from(Sala.class);
        cd.where(cb.equal(root.get("id"), id));
        entityManager.createQuery(cd).executeUpdate();
    }
}
