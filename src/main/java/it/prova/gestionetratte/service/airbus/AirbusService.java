package it.prova.gestionetratte.service.airbus;

import java.util.List;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.model.Airbus;

public interface AirbusService {

	List<Airbus> listAll();
	
	List<Airbus> listAllEager();
	
	Airbus caricaSingoloElemento(Long id);
	
	Airbus caricaSingoloElementoEager(Long id);
	
	Airbus aggiorna(Airbus airbusInstance);
	
	Airbus inserisciNuovo(Airbus airbusInstance);
	
	void rimuovi(Long idToRemove);
	
	List<Airbus> findByExample(Airbus example);
	
	List<AirbusDTO> findListaAirbusEvidenziandoSovrapposizioni();
}
