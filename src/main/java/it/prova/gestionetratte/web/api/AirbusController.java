package it.prova.gestionetratte.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.service.airbus.AirbusService;
import it.prova.gestionetratte.web.api.exception.AirbusNotFoundException;
import it.prova.gestionetratte.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

	@Autowired
	private AirbusService airbusService;

	@GetMapping
	public List<AirbusDTO> listAll() {
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllEager(), true);
	}

	@GetMapping("/{id}")
	public AirbusDTO findById(@PathVariable(value = "id", required = true) long id) {
		Airbus airbus = airbusService.caricaSingoloElementoEager(id);

		if (airbus == null)
			throw new AirbusNotFoundException("Airbus not found con id: " + id);

		return AirbusDTO.buildAirbusDTOFromModel(airbus, true);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AirbusDTO createNew(@Valid @RequestBody AirbusDTO airbusInput) {
		if (airbusInput.getId() != null)
			throw new IdNotNullForInsertException("Non puoi inserire airbus con id associati");

		Airbus airbusInserito = airbusService.inserisciNuovo(airbusInput.buildAirbusModel());
		return AirbusDTO.buildAirbusDTOFromModel(airbusInserito, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		airbusService.rimuovi(id);
	}

	@PutMapping("/{id}")
	public AirbusDTO update(@Valid @RequestBody AirbusDTO airbusInput, @PathVariable(required = true) Long id) {

		Airbus airbus = airbusService.caricaSingoloElementoEager(id);

		if (airbus == null) {
			throw new AirbusNotFoundException("Airbus not found con id: " + id);
		}

		airbusInput.setId(id);

		Airbus airbusAggiornato = airbusService.aggiorna(airbusInput.buildAirbusModel());

		return AirbusDTO.buildAirbusDTOFromModel(airbusAggiornato, false);
	}

	@PostMapping("/search")
	public List<AirbusDTO> search(@RequestBody AirbusDTO example) {
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.findByExample(example.buildAirbusModel()),
				false);
	}

	@GetMapping("/listaAirbusEvidenziandoSovrapposizioni")
	public List<AirbusDTO> getListaAirbusEvidenziandoSovrapposizioni() {
		return airbusService.findListaAirbusEvidenziandoSovrapposizioni();
	}
}
