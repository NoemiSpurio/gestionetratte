package it.prova.gestionetratte.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.airbus.AirbusService;
import it.prova.gestionetratte.service.tratta.TrattaService;
import it.prova.gestionetratte.web.api.exception.IdNotNullForInsertException;
import it.prova.gestionetratte.web.api.exception.TrattaNotFoundException;
import it.prova.gestionetratte.web.api.exception.TrattaValidationException;

@RestController
@RequestMapping("api/tratta")
public class TrattaController {

	@Autowired
	private TrattaService trattaService;

	@Autowired
	private AirbusService airbusService;

	@GetMapping
	public List<TrattaDTO> getAll() {
		return TrattaDTO.createTrattaDTOListFromModelList(trattaService.listAll(true), true);
	}

	@GetMapping("/{id}")
	public TrattaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Tratta tratta = trattaService.caricaSingoloElementoEager(id);

		if (tratta == null)
			throw new TrattaNotFoundException("Tratta not found con id: " + id);

		return TrattaDTO.buildTrattaDTOFromModel(tratta, true);
	}

	@PostMapping
	public TrattaDTO createNew(@Valid @RequestBody TrattaDTO trattaInput) {

		if (trattaInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Airbus airbusPerValidazioni = airbusService.caricaSingoloElemento(trattaInput.getAirbus().getId());

		if (trattaInput.getData().isBefore(airbusPerValidazioni.getDataInizioServizio()))
			throw new TrattaValidationException(
					"Non e' possibile inserire una tratta con data di partenza precedente a quella di inizio servizio dell airbus.");

		if (trattaInput.getOraDecollo().isAfter(trattaInput.getOraAtterraggio()))
			throw new TrattaValidationException(
					"Non e' possibile inserire una tratta con orario di decollo successivo a quello di atterraggio.");

		Tratta trattaInserita = trattaService.inserisciNuovo(trattaInput.buildTrattaModel());
		return TrattaDTO.buildTrattaDTOFromModel(trattaInserita, true);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		trattaService.rimuovi(id);
	}

	@PostMapping("/search")
	public List<TrattaDTO> search(@RequestBody TrattaDTO example) {
		return TrattaDTO.createTrattaDTOListFromModelList(trattaService.findByExample(example.buildTrattaModel()),
				false);
	}
}
