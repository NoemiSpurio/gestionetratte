package it.prova.gestionetratte.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.service.airbus.AirbusService;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

	@Autowired
	private AirbusService airbusService;
	
	@GetMapping
	public List<AirbusDTO> listAll(){
		return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllEager(), true);
	}
}
