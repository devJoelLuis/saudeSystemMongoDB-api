package saude.api.controller.transporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import saude.api.dto.transporteDto.AgendaDto;
import saude.api.entity.transporte.Agenda;
import saude.api.entity.transporte.AgendaPost.AgendarPost;
import saude.api.filtros.AgendaSearchDataNomeDTO;
import saude.api.response.Response;
import saude.api.service.transporte.AgendaService;

@RestController
@RequestMapping("/agendas")
public class AgendaController {
	
	
	@Autowired
	private AgendaService service;
	
	
	
	@PostMapping
	public ResponseEntity<Response<String>> criarAgendas(
			@RequestBody Agenda agenda
			){
		Response<String> response = service.criarAgenda(agenda);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	
	
	
	
	
	@PostMapping("/agendar")
	public ResponseEntity<Response<Agenda>> agendarCidadao(
			@RequestBody AgendarPost ap
			) {
		Response<Agenda> response = service.agendarCidadao(ap);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	

	  //buscar uma agenda por nome e intervalo de data
	 @GetMapping("/datas")
	 public ResponseEntity<Response<Page<AgendaDto>>> buscarAgendaNomeDatas(AgendaSearchDataNomeDTO agendaSearchDTO
			 ){
		 Response<Page<AgendaDto>> response = service.buscarPorNomeIntervaloData(agendaSearchDTO);
		 if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
	 }
	
	
	
	//buscar todas agendas por parte do nome da agenda, e uma determinada data
	 @GetMapping("/nomes")
	 public ResponseEntity<Response<Page<AgendaDto>>> buscarAgendaNomes(
			 @RequestParam(value="nome", defaultValue="")String nome,
			 @RequestParam(value="data", defaultValue="")String data,
			 @RequestParam(value="page", defaultValue="0") int page,
			 @RequestParam(value="size", defaultValue="8") int size
			 ){
		 Response<Page<AgendaDto>> response = service.buscarAgendaNomes(nome, data, page, size);
		 if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
	 }
	  	
	 
	 
	 
	   /*
		
			//verificar se cidadão já possui alguma agenda no dia
			@PreAuthorize("hasAuthority('ROLE_ADMIN_TRANSPORTE')")
			@GetMapping("/agendadata")
			public ResponseEntity<Response<List<Agenda>>> buscarEmTodasAgendasDoDiaCidadao(
					@RequestParam(value="nomePaciente", defaultValue="") String idcidadao,
					@RequestParam(value="data", defaultValue="") String data
					) {
				Response<List<Agenda>> response = service. buscarEmTodasAgendasDoDiaCidadao(idcidadao, data);
				if(response.getErros().size() > 0) {
					return ResponseEntity.badRequest().body(response);
				}
				return ResponseEntity.ok(response);
			}
		
		*/
	
	
	
	
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN_TRANSPORTE')")
	@DeleteMapping
	public ResponseEntity<Response<String>> deletarAgenda(
			@RequestParam(value="idagenda", defaultValue="") String idagenda
			){
		Response<String> response = service.removerAgenda(idagenda);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	//deleta um paciente ou um acompanhante da agenda
	@PreAuthorize("hasAuthority('ROLE_ADMIN_TRANSPORTE') OR hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/agenda")
	public ResponseEntity<Response<Agenda>> cancelarAgendamentoCidadao(
			@RequestParam(value="idagenda", defaultValue="") String idagenda,
			@RequestParam(value="nomePaciente", defaultValue="") String nomePaciente,
			@RequestParam(value="nomeAcompanhante", defaultValue="") String nomeAcompanhante
			){
		Response<Agenda> response = service.removerAgendaCidadao(idagenda, nomePaciente, nomeAcompanhante);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	//buscar agenda por id
	@GetMapping("/agenda")
	public ResponseEntity<Response<Agenda>> buscarPorId(
			@RequestParam(value="id", defaultValue="") String idagenda
			){
		Response<Agenda> response = service.buscarPorId(idagenda);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	//buscar todas as agendas de um paciente, pelo nome do paciente
	 @GetMapping("/agendasPaciente")
	 public ResponseEntity<Response<Page<AgendaDto>>> buscarAgendaNomes(
			 @RequestParam(value="nomePaciente", defaultValue="")String nome,
			 @RequestParam(value="page", defaultValue="0") int page,
			 @RequestParam(value="size", defaultValue="8") int size
			 ){
		 Response<Page<AgendaDto>> response = service.buscarAgendasPaciente(nome, page, size);
		 if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
	 }
	
	
	
	
	
}//fecha classe
