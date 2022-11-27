package it.prova.gestionetratte.service.tratta;

import java.util.List;

import it.prova.gestionetratte.model.Tratta;

public interface TrattaService {

	List<Tratta> listAll(boolean eager);
	
	Tratta caricaSingoloElemento(Long id);
	
	Tratta caricaSingoloElementoEager(Long id);
	
	Tratta aggiorna(Tratta trattaInstance);
	
	Tratta inserisciNuovo(Tratta trattaInstance);
	
	void rimuovi(Long idToRemove);
	
	List<Tratta> findByExample(Tratta example);
}
