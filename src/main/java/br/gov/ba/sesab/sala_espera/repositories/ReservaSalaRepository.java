package br.gov.ba.sesab.sala_espera.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.ba.sesab.sala_espera.domains.ReservaSala;
import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.domains.UnidadeSaude;
import br.gov.ba.sesab.sala_espera.domains.Usuario;

@Repository
public interface ReservaSalaRepository extends JpaRepository<ReservaSala, Integer> {
	
	@Transactional
	@Modifying
    @Query("UPDATE #{#entityName} e SET e.status = ?1 WHERE e.id = ?2")
    void editarStatus(Integer idStatus, Integer id);
	
	@Query(value = "SELECT COUNT(r.id) FROM reserva_sala r WHERE r.id_sala = :sala AND r.status not in (2, 3) "
			+ "AND r.id <> :idReservaAtual AND ( "
			+ "r.dt_inicio BETWEEN :inicio AND :fim OR r.dt_final BETWEEN :inicio AND :fim "
			+ "OR :inicio BETWEEN r.dt_inicio AND r.dt_final OR :fim BETWEEN r.dt_final AND r.dt_final "
			+ ");", nativeQuery = true)
    long contarReservasConflitantes(
	        @Param("sala") Integer sala,
	        @Param("inicio") LocalDateTime inicio,
	        @Param("fim") LocalDateTime fim,
	        @Param("idReservaAtual") Integer idReservaAtual
    );
	
	/**
     * Lista todas as Salas que possuem reservas ativas (não canceladas) que se
     * sobrepõem ao período fornecido e pertencem à Unidade de Saúde.
     */
    @Query("SELECT DISTINCT r.sala FROM ReservaSala r " +
           "WHERE r.sala.unidadeSaude = :unidade " +
           "  AND r.status <> 2 " +
           "  AND ( (r.dataInicio < :fim) AND (r.dataFinal > :inicio) )")
    List<Sala> findSalasReservadasNoPeriodo(
        @Param("unidade") UnidadeSaude unidade,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );
    
    List<ReservaSala> findByUsuario(Usuario usuario);
    List<ReservaSala> findBySala(Sala sala);
    
    @Transactional
	@Modifying
    @Query("UPDATE #{#entityName} e SET e.status = ?1 WHERE e.id IN (?2)")
    void arquivarReservaSala(Integer idStatus, List<Integer> idsReservaSalaString);
}
