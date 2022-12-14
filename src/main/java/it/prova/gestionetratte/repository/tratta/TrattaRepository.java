package it.prova.gestionetratte.repository.tratta;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;

public interface TrattaRepository extends CrudRepository<Tratta, Long>, CustomTrattaRepository {

	@Query("from Tratta t join fetch t.airbus where t.id = ?1")
	Tratta findByIdEager(Long id);
	
	@Query("select t from Tratta t join fetch t.airbus")
	List<Tratta> findAllEager();

	List<Tratta> findAllByStatoLike(Stato stato);
}
