package saude.api.controller.transporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import saude.api.entity.transporte.Motorista;
import saude.api.response.Response;
import saude.api.service.transporte.MotoristaService;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {
	
	@Autowired
	private MotoristaService service;
	
	//get all
	@GetMapping
	public ResponseEntity<Response<Page<Motorista>>> findAll(
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="8") int size
			){
		Response<Page<Motorista>> response = service.buscarTodosMotoristas(page, size);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	//buscar por id
	@GetMapping("/motorista")
	public ResponseEntity<Response<Motorista>> findById(
			@RequestParam(value="id", defaultValue="") String id
			){
		Response<Motorista> response = service.buscarPorId(id);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	    //get all
		@GetMapping("/nome")
		public ResponseEntity<Response<Page<Motorista>>> findAllNome(
				@RequestParam(value="page", defaultValue="0") int page,
				@RequestParam(value="size", defaultValue="8") int size,
				@RequestParam(value="nome", defaultValue="") String nome
				){
			Response<Page<Motorista>> response = service.buscarPorNome(nome, page, size);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
		
	
		
		
		
		
	//post cadastrar novo motorista
	@PostMapping
	public ResponseEntity<Response<Motorista>> cadastrarNovoMotorista(
			@RequestBody Motorista motorista
			) {
		Response<Motorista> response = service.cadastrarNovoMotorista(motorista);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	    //put cadastrar novo motorista
		@PutMapping
		public ResponseEntity<Response<Motorista>> AlterarMotorista(
				@RequestBody Motorista motorista
				) {
			Response<Motorista> response = service.AlterarMotorista(motorista);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
	
		
		
		
	
		// delete motorista
		@DeleteMapping
		public ResponseEntity<Response<String>> deletarMotorista(
				@RequestParam(value="id", defaultValue="") String id
				){
			Response<String> response = service.deletarMotorista(id);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
	
	

}//fecha classe
