package saude.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import saude.api.dto.transporteDto.FaltaTransporteDto;
import saude.api.entity.Cidadao;
import saude.api.entity.Falta;
import saude.api.response.Response;
import saude.api.service.CidadaoService;

@RestController
@RequestMapping(value="/cidadaos")
public class CidadaoController {
	
	
	
	
	@Autowired
	private CidadaoService service;
	
	
	
	
	//adicinar falta ao cidadao
	@PostMapping("/falta")
	public ResponseEntity<Response<Cidadao>> adicionarFalta(
			@RequestBody Falta falta,
			@RequestParam(value="idcidadao", defaultValue="")String idcidadao
			){
		Response<Cidadao> response = service.adicionarFalta(falta, idcidadao); 
		 if(response.getErros().size() >0 ) {
			  return ResponseEntity.badRequest().body(response);
		  }
		  return ResponseEntity.ok(response);
	}
	
	
	//define cidadao como faltoso no transporte
	@PostMapping("/faltaTransporte")
	public ResponseEntity<Response<Cidadao>> faltaNoTransporte (
			@RequestBody FaltaTransporteDto ft
			) {
		Response<Cidadao> response = service.faltaTransporte(ft);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	//liberar falta url = cidadaos/libera?data=2018-08-02&idcidadao=ksldfjsdkl
	@PostMapping("/libera")
	public ResponseEntity<Response<Cidadao>> liberarCidadao(
			@RequestParam(value="data", defaultValue="") String data ,
			@RequestParam(value="idcidadao", defaultValue="")String idcidadao
			){
		Response<Cidadao> response = service.liberarCidadao(data, idcidadao); 
		 if(response.getErros().size() >0 ) {
			  return ResponseEntity.badRequest().body(response);
		  }
		  return ResponseEntity.ok(response);
	}
	
	//liberar falta do transporte url = cidadaos/liberaTransporte?data=2018-08-02&idcidadao=ksldfjsdkl
		@PostMapping("/liberaTransporte")
		public ResponseEntity<Response<Cidadao>> liberarCidadaoTransporte(
				@RequestParam(value="data", defaultValue="") String data ,
				@RequestParam(value="idcidadao", defaultValue="")String idcidadao
				){
			Response<Cidadao> response = service.liberarCidadaoTransporte(data, idcidadao); 
			 if(response.getErros().size() >0 ) {
				  return ResponseEntity.badRequest().body(response);
			  }
			  return ResponseEntity.ok(response);
		}
		
	
	
	
	@GetMapping(value="{page}/{size}")
	public ResponseEntity<Response<Page<Cidadao>>> findAll(
			@PathVariable("page") int page,
			@PathVariable("size") int size
			
	   ){
		Response<Page<Cidadao>> response = new Response<Page<Cidadao>>();
		
		try {
			Page<Cidadao> cidadaoPage = service.findAll(page, size);
			response.setDados(cidadaoPage);
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	

	//pesquisa o cidadao
	@GetMapping(value="/cidadao")
	public ResponseEntity<Response<Cidadao>> findByCidadao(
			@RequestParam(value = "id", defaultValue ="") String id, 
			@RequestParam(value = "prontuario", defaultValue = "0") String prontuario,
			@RequestParam(value = "cartaosus", defaultValue = "") String cartaosus
			) {
		Response<Cidadao> response = service.findCidadao(id, prontuario, cartaosus);
		if(response.getErros().size() > 0) {
			return ResponseEntity.badRequest().body(response);
		} else {
			return ResponseEntity.ok(response);
		}
	}
	
	
	
	//pesquisar por parte do nome, numero prontuario, numero cartao cidadao, numero cartao sus
	 @GetMapping(value= "/parametro")
	   public ResponseEntity<Response<Page<Cidadao>>> findByParamAll(
			   @RequestParam(value="param", defaultValue="") String param,
			   @RequestParam(value="page", defaultValue="0") int page,
			   @RequestParam(value="size", defaultValue="8") int size
			   ){
		   Response<Page<Cidadao>> resposta  = service.findByParamAll(param, page, size);
		   if(resposta.getErros().size() > 0) {
			   return ResponseEntity.badRequest().body(resposta);
		   } else {
			   return ResponseEntity.ok(resposta);
		   }
	   }
	
	
	
	 // url cidadaos/nome?nome='parte do nome do cidadao'&pages=0&size=8
   @GetMapping(value= "/nome")
   public ResponseEntity<Response<Page<Cidadao>>> findByNome(
		   @RequestParam(value="nome", defaultValue="") String nome,
		   @RequestParam(value="pages", defaultValue="0") int pages,
		   @RequestParam(value="size", defaultValue="8") int size
		   
		   ){
	   Response<Page<Cidadao>> resposta = new Response<Page<Cidadao>>();
	   resposta = service.findByNome(nome, pages, size);
	   if(resposta.getErros().size() > 0) {
		   return ResponseEntity.badRequest().body(resposta);
	   } else {
		   return ResponseEntity.ok(resposta);
	   }
   }
   
   // url cidadaos/nome?nome='parte do nome do cidadao'&pages=0&size=8
   @GetMapping(value= "/nomequery")
   public ResponseEntity<Response<List<String>>> findByNomeAutoComplet(
		   @RequestParam(value="nome", defaultValue="") String nome,
		   @RequestParam(value="pages", defaultValue="0") int pages,
		   @RequestParam(value="size", defaultValue="10") int size
		   
		   ){
	   if(size > 30) size = 30;// proteção contra excessos
	   Response<List<String>> resposta  = service.findByNomeQuery(nome, pages, size);
	   if(resposta.getErros().size() > 0) {
		   return ResponseEntity.badRequest().body(resposta);
	   } else {
		   return ResponseEntity.ok(resposta);
	   }
   }
   
   
   
   // url cidadaos/nome?nome='parte do nome do cidadao
   @GetMapping(value= "/nomecompleto")
   public ResponseEntity<Response<Cidadao>> findByNomeCompleto(
		   @RequestParam(value="nome", defaultValue="") String nome
		   ){
	   Response<Cidadao> resposta  = service.findByNomeCompleto(nome);
	   if(resposta.getErros().size() > 0) {
		   return ResponseEntity.badRequest().body(resposta);
	   } else {
		   return ResponseEntity.ok(resposta);
	   }
   }
   
   
   
   // url cidadaos/cartaocidadao?cartaocid='numerodocartao'
   @GetMapping(value= "/cartaocidadao")
   public ResponseEntity<Response<Cidadao>> findByCartaoCidadao(
		   @RequestParam(value="cartaocid", defaultValue="") String ccid
		   ){
	   Response<Cidadao> resposta  = service.findByCartaoCidadao(ccid);
	   if(resposta.getErros().size() > 0) {
		   return ResponseEntity.badRequest().body(resposta);
	   } else {
		   return ResponseEntity.ok(resposta);
	   }
   }
   
   
   // url cidadaos/numeroprontuario?numpront='numerodocartao'
   @GetMapping(value= "/numeroprontuario")
   public ResponseEntity<Response<Cidadao>> findByNumeroProntuario(
		   @RequestParam(value="numpront", defaultValue="") String num
		   ){
	   Response<Cidadao> resposta  = service.findByNumeroProntuario(num);
	   if(resposta.getErros().size() > 0) {
		   return ResponseEntity.badRequest().body(resposta);
	   } else {
		   return ResponseEntity.ok(resposta);
	   }
   }
   
   // url cidadaos/cartaosus?numsus='numerodocartao'
   @GetMapping(value= "/numerocartaosus")
   public ResponseEntity<Response<Cidadao>> findByNumeroCartaoSus(
		   @RequestParam(value="numsus", defaultValue="") String num
		   ){
	   Response<Cidadao> resposta  = service.findByNumeroCartaoSus(num);
	   if(resposta.getErros().size() > 0) {
		   return ResponseEntity.badRequest().body(resposta);
	   } else {
		   return ResponseEntity.ok(resposta);
	   }
   }
   
   
   
  @PostMapping
  public ResponseEntity<Response<Cidadao>> cadastrarCidadao(
		  @RequestBody Cidadao cidadao 
		  ){
	  Response<Cidadao> resposta = service.cadastrarCidadao(cidadao); 
	  if(resposta.getErros().size() > 0) {
		   return ResponseEntity.badRequest().body(resposta);
	   } else {
		   return ResponseEntity.ok(resposta);
	   }
  }
   
  
  @PutMapping
  public ResponseEntity<Response<Cidadao>> alterarCidadao(
		  @RequestBody Cidadao cidadao
		  ){
	  Response<Cidadao> response = service.alterarCidadao(cidadao); 
	  if(response.getErros().size() >0 ) {
		  return ResponseEntity.badRequest().body(response);
	  }
	  return ResponseEntity.ok(response);
  }
  
  
  
  
  
   

}
