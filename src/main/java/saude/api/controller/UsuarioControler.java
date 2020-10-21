package saude.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import saude.api.entity.Usuario;
import saude.api.response.Response;
import saude.api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControler {
	
	@Autowired
	private UsuarioService service;
	
	
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Response<Usuario>> cadastro(
			@RequestBody Usuario user
			){
		Response<Usuario> response = service.cadastrarNovo(user);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<Response<Page<Usuario>>> listarTodos(
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="8") int size
			){
		if(size > 25) size = 25;
		Response<Page<Usuario>> response = service.listarTodos(page, size);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	@GetMapping("/{iduser}")
	public ResponseEntity<Response<Usuario>> buscarPorId(
			@PathVariable("iduser") String iduser
			){
		
		Response<Usuario> response = service.buscarPorId(iduser);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	@GetMapping("/usuario")
	public ResponseEntity<Response<Page<Usuario>>> buscarPorParteNome(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="page", defaultValue="") int page,
			@RequestParam(value="size", defaultValue="") int size
			){
		
		Response<Page<Usuario>> response = service.buscarPorParteNome(nome, page, size);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

}
