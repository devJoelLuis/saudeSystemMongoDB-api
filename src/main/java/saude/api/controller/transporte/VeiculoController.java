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

import saude.api.entity.transporte.Veiculo;
import saude.api.response.Response;
import saude.api.service.transporte.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
	
	@Autowired
	private VeiculoService service;
	
	//get all
	@GetMapping
	public ResponseEntity<Response<Page<Veiculo>>> findAll(
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="8") int size
			){
		Response<Page<Veiculo>> response = service.buscarTodosVeiculos(page, size);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	//buscar por id
	@GetMapping("/veiculo")
	public ResponseEntity<Response<Veiculo>> findById(
			@RequestParam(value="id", defaultValue="") String id
			){
		Response<Veiculo> response = service.buscarPorId(id);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	    //get all
		@GetMapping("/nome")
		public ResponseEntity<Response<Page<Veiculo>>> findAll(
				@RequestParam(value="page", defaultValue="0") int page,
				@RequestParam(value="size", defaultValue="8") int size,
				@RequestParam(value="nome", defaultValue="") String nome
				){
			Response<Page<Veiculo>> response = service.buscarPorNome(nome, page, size);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
		
	
		
	//get all nome ou placa	
		@GetMapping("/nome-ou-placa")
		public ResponseEntity<Response<Page<Veiculo>>> findNomeOuPlaca(
				@RequestParam(value="page", defaultValue="0") int page,
				@RequestParam(value="size", defaultValue="8") int size,
				@RequestParam(value="parametro", defaultValue="") String param
				){
			Response<Page<Veiculo>> response = service.buscarPorNomeOuPlaca(param, page, size);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}	
		
		
		
	//post cadastrar novo veiculo
	@PostMapping
	public ResponseEntity<Response<Veiculo>> cadastrarNovoVeiculo(
			@RequestBody Veiculo veiculo
			) {
		Response<Veiculo> response = service.cadastrarNovoVeiculo(veiculo);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	    //put cadastrar novo veiculo
		@PutMapping
		public ResponseEntity<Response<Veiculo>> AlterarVeiculo(
				@RequestBody Veiculo veiculo
				) {
			Response<Veiculo> response = service.AlterarVeiculo(veiculo);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
	
		
		
		
	
		// delete veiculo
		@DeleteMapping
		public ResponseEntity<Response<String>> deletarVeiculo(
				@RequestParam(value="id", defaultValue="") String id
				){
			Response<String> response = service.deletarVeiculo(id);
			if(response.getErros().size() > 0) {
				return ResponseEntity.badRequest().body(response);
			}
			return ResponseEntity.ok(response);
		}
	
	

}//fecha classe
