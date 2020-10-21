package saude.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import saude.api.entity.Ubs;
import saude.api.response.Response;
import saude.api.service.UbsService;



@RestController
@RequestMapping(value="/ubs")
public class UbsController {
	
	
	@Autowired
	private UbsService service;
	
	
	@GetMapping(value="{page}/{size}")
	public ResponseEntity<Response<Page<Ubs>>> listarTodas(
			@PathVariable int page, 
			@PathVariable int size ){
		Response<Page<Ubs>> response = new Response<Page<Ubs>>();
		try {
			Pageable pageable = new PageRequest(page, size);
			Page<Ubs> pageUbs = service.findAll(pageable);
			response.setDados(pageUbs);
			return ResponseEntity.ok(response);	
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
			
		}
			
				
	}
	
	
	@GetMapping
	public ResponseEntity<Response<Ubs>> buscarPorIp(
			@RequestParam(value="idubs", defaultValue="") String idubs
			){
		Response<Ubs> response = service.buscarPorId(idubs);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping
	public ResponseEntity<Response<Ubs>> alterarUbs(
			@RequestBody Ubs ubs
			){
		Response<Ubs> response = service.alterarUbs(ubs, "iduser");// TODO: pegar id user do token
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Response<Ubs>> cadastrarNovaUbs(
			@RequestBody Ubs ubs
			){
		Response<Ubs> response = service.cadastrarUbs(ubs); // TODO: pegar id do user do token
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping
	public ResponseEntity<Response<String>> deletarUbs(
			@RequestParam(value="idubs", defaultValue="") String idubs
			){
		Response<String> response = service.deletarUbs(idubs, "iduser"); // TODO : pegar id do user no token
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	

}//fecha controller
