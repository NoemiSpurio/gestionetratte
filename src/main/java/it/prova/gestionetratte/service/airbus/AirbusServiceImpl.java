package it.prova.gestionetratte.service.airbus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;
import it.prova.gestionetratte.web.api.exception.AirbusConTratteAssociateException;
import it.prova.gestionetratte.web.api.exception.AirbusNotFoundException;

@Service
public class AirbusServiceImpl implements AirbusService {

	@Autowired
	private AirbusRepository repository;
	
	@Override
	public List<Airbus> listAll() {
		return (List<Airbus>) repository.findAll();
	}

	@Override
	public List<Airbus> listAllEager() {
		return (List<Airbus>) repository.findAllEager();
	}

	@Override
	public Airbus caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Airbus caricaSingoloElementoEager(Long id) {
		return repository.findByIdEager(id);
	}

	@Override
	@Transactional
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		Airbus airbusToBeRemoved = repository.findByIdEager(idToRemove);

		if (airbusToBeRemoved == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + idToRemove);

		if (airbusToBeRemoved.getTratte().size() > 0)
			throw new AirbusConTratteAssociateException(
					"Impossibile eliminare airbus: sono ancora presenti tratte associate.");

		repository.deleteById(idToRemove);
	}

	@Override
	public List<Airbus> findByExample(Airbus example) {
		return repository.findByExample(example);
	}

	@Override
	public List<AirbusDTO> findListaAirbusEvidenziandoSovrapposizioni() {
		
		List<AirbusDTO> listAirbusEager = AirbusDTO.createAirbusDTOListFromModelList(repository.findAllEager(), true);
		for (AirbusDTO airbusItem : listAirbusEager) {
			for (TrattaDTO trattaItem : airbusItem.getTratte()) {
				for (TrattaDTO item : airbusItem.getTratte()) {
					if ((item.getData().isEqual(trattaItem.getData()) && item.getOraDecollo().isAfter(trattaItem.getOraDecollo())
							&& item.getOraDecollo().isBefore(trattaItem.getOraAtterraggio()))
							|| (item.getData().isEqual(trattaItem.getData()) && item.getOraAtterraggio().isAfter(trattaItem.getOraDecollo())
									&& item.getOraAtterraggio().isBefore(trattaItem.getOraAtterraggio()))) {
						airbusItem.setConSovrapposizioni(true);

					}
				}
			}
		}
		
		return listAirbusEager;
	}

}
