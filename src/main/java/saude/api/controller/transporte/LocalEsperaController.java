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

import saude.api.entity.transporte.LocalEspera;
import saude.api.response.Response;
import saude.api.service.transporte.LocalEsperaService;

@RestController
@RequestMapping("/localEspera")
public class LocalEsperaController {
	
	@Autowired
	private LocalEsperaService service;
	
	//get all
	@GetMapping
	public ResponseEntity<Response<Page<LocalEspera>>> findAll(
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="8") int size
			) {
		Response<Page<LocalEspera>> response = service.buscarTodosLocalEsperas(page, size);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	//buscar por id
	@GetMapping("/localEspera")
	public ResponseEntity<Response<LocalEspera>> findById(
			@RequestParam(value="id", defaultValue="") String id
			){
		Response<LocalEspera> response = service.buscarPorId(id);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	    //get all
		@GetMapping("/nome")
		public ResponseEntity<Response<Page<LocalEspera>>> findAll(
				@RequestParam(value="page", defaultValue="0") int page,
				@RequestParam(value="size", defaultValue="8") int size,
				@RequestParam(value="nome", defaultValue="") String nome
				){
			Response<Page<LocalEspera>> response = service.buscarPorNome(nome, page, size);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
		
	
		
		
		
		
	//post cadastrar novo localEspera
	@PostMapping
	public ResponseEntity<Response<LocalEspera>> cadastrarNovoLocalEspera(
			@RequestBody LocalEspera localEspera
			) {
		Response<LocalEspera> response = service.cadastrarNovoLocalEspera(localEspera);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	    //put cadastrar novo localEspera
		@PutMapping
		public ResponseEntity<Response<LocalEspera>> AlterarLocalEspera(
				@RequestBody LocalEspera localEspera
				) {
			Response<LocalEspera> response = service.AlterarLocalEspera(localEspera);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
	
		
		
		
	
		// delete localEspera
		@DeleteMapping
		public ResponseEntity<Response<String>> deletarLocalEspera(
				@RequestParam(value="id", defaultValue="") String id
				){
			Response<String> response = service.deletarLocalEspera(id);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
	
	

}//fecha classe
