package it.prova.gestionetratte.service.tratta;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;
import it.prova.gestionetratte.web.api.exception.RimozioneTrattaNonAnnullataException;
import it.prova.gestionetratte.web.api.exception.TrattaNotFoundException;

@Service
public class TrattaServiceImpl implements TrattaService {

	@Autowired
	private TrattaRepository repository;
	
	@Override
	public List<Tratta> listAll(boolean eager) {
		if (eager) {
			return (List<Tratta>) repository.findAllEager();
		}
		return (List<Tratta>) repository.findAll();
	}

	@Override
	public Tratta caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Tratta caricaSingoloElementoEager(Long id) {
		return repository.findByIdEager(id);
	}

	@Override
	@Transactional
	public Tratta aggiorna(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	@Transactional
	public Tratta inserisciNuovo(Tratta trattaInstance) {
		return repository.save(trattaInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		Tratta trattaToBeRemoved = repository.findById(idToRemove).orElse(null);
		
		if (trattaToBeRemoved == null) {
			throw new TrattaNotFoundException("Tratta not found con id: " + idToRemove);
		}
		
		if (trattaToBeRemoved.getStato() == Stato.ATTIVA || trattaToBeRemoved.getStato() == Stato.CONCLUSA)
			throw new RimozioneTrattaNonAnnullataException(
					"Impossibile rimuovere tratta: deve essere prima ANNULLATA.");

		repository.deleteById(idToRemove);
	}

	@Override
	public List<Tratta> findByExample(Tratta example) {
		return repository.findByExample(example);
	}

	@Override
	@Transactional
	public void concludiTratte() {
		List<Tratta> tratteAttive = repository.findAllByStatoLike(Stato.ATTIVA);

		if (tratteAttive.size() == 0)
			return;

		tratteAttive.stream().filter(trattaItem -> LocalTime.now().isAfter(trattaItem.getOraAtterraggio()))
				.forEach(trattaItem -> trattaItem.setStato(Stato.CONCLUSA));
		tratteAttive.forEach(trattaItem -> repository.save(trattaItem));
	}

}
