package saude.api.service.transporte;


import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import saude.api.entity.transporte.HistoricoTransporte;
import saude.api.entity.transporte.LocalEspera;
import saude.api.repository.transporte.HistoricoTransporteRepository;
import saude.api.repository.transporte.LocalEsperaRepository;
import saude.api.response.Response;

@Service
public class LocalEsperaService {
	
	@Autowired
	private LocalEsperaRepository repo;
	
	
	@Autowired
	private HistoricoTransporteRepository repoHist;
	
	
	
	//busca todos os localEsperas
	public Response<Page<LocalEspera>> buscarTodosLocalEsperas(int page, int size) {
		Response<Page<LocalEspera>> response = new Response<>();
		try {
			
			Pageable pageable = new PageRequest(page, size);
			Page<LocalEspera> localEsperasPage = repo.findAll(pageable);
			response.setDados(localEsperasPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
		
	}
	
	
	//busca por id do localEspera
	public Response<LocalEspera> buscarPorId(String id) {
		Response<LocalEspera> response = new Response<>();
		try {
			if(id == null || id.equals("")) {
				response.getErros().add("Erro: o id do Local de Espera não foi informado!!");
				return response;
			}
			
			LocalEspera localEspera = repo.findOne(id);
			if(localEspera == null) {
				response.getErros().add("Não foi encontrado nenhum Local de Espera com o id informado!!!");
				return response;
			}
			
			
			response.setDados(localEspera);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	//buscar localEspera por parte do nome
	
	public Response<Page<LocalEspera>> buscarPorNome(String nome, int page, int size) {
		Response<Page<LocalEspera>> response = new Response<>();
		try {
			Page<LocalEspera> localEsperasPage;
			Pageable pageable = new PageRequest(page, size);
			if(nome == null || nome.equals("")) {
				localEsperasPage = repo.findAll(pageable);
				response.setDados(localEsperasPage);
				return response;
			}
			
			localEsperasPage = repo.findByLocalLikeIgnoreCase(nome, pageable);
			response.setDados(localEsperasPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	
	//cadastrar novo localEspera
	public Response<LocalEspera> cadastrarNovoLocalEspera(LocalEspera v) {
		Response<LocalEspera> response = new Response<>();
		try {
			String validar = validarParaCadastro(v);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//confirmar que nenhum id está sendo passado
			v.setId(null);
			//cria um novo historico
			HistoricoTransporte ht = new HistoricoTransporte();
			ht.setAcao("Cadastrado de novo Local de Espera "+v.getLocal());
			ht.setDataHora(LocalDateTime.now());
			ht.setId(null);
			ht.setUsuario(buscarUsuario());
			LocalEspera vSalvo = repo.save(v);
			response.setDados(vSalvo);
			repoHist.save(ht);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	
	
	
	private String validarParaCadastro(LocalEspera v) {
		try {
			if(v.getLocal() == null || v.getLocal().equals("")) {
				return "Erro: O nome do Local de Espera não foi informado";
			}
			return "ok";
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
	
	
	//Alterar localEspera
		public Response<LocalEspera> AlterarLocalEspera(LocalEspera l) {
			Response<LocalEspera> response = new Response<>();
			try {
				String validar = validarParaAlteracao(l);
				if(!validar.equals("ok")) {
					response.getErros().add(validar);
					return response;
				}
				//criar historico
				HistoricoTransporte ht = new HistoricoTransporte();
				ht.setAcao("Alterado Local de Espera "+l.getLocal());
				ht.setDataHora(LocalDateTime.now());
				ht.setId(null);
				ht.setUsuario(buscarUsuario());
				//buscar local espera atual
				LocalEspera localAtual = repo.findOne(l.getId());
				if(localAtual == null) {
					response.getErros().add("Erro: Não foi possível encontrar o "
							+ "local de espera com o valor do id passado");
				}
				localAtual = l;
				LocalEspera vSalvo = repo.save(localAtual);
				response.setDados(vSalvo);
				repoHist.save(ht);
				return response;
			} catch (Exception e) {
				response.getErros().add(e.getMessage());
				return response;
			}
		}
	
	
	
	
	


	private String validarParaAlteracao(LocalEspera v) {
		try {
			if(v.getId() == null || v.getId().equals("")) {
				return "Erro: O id do Local de Espera não foi informado!!";
			}
			if(v.getLocal() == null || v.getLocal().equals("")) {
				return "Erro: Nome do Local de Espera não informado";
			}
			Long countId = repo.countById(v.getId());
			if(countId == 0) {
				return "Erro: Não foi possível encontrar um Local de Espera com o id informador!!!";
			}
			return "ok";
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
	
	
	
	 // deletar localEsperas
	 public Response<String> deletarLocalEspera(String id) {
		 Response<String> response = new Response<>();
		 try {
			if(id == null || id.equals("")) {
				response.getErros().add("Erro: O id do Local de Espera a ser deletado não foi informado!!!");
				return response;
			}
			LocalEspera v = repo.findOne(id);
			if(v == null) {
				response.getErros().add("Erro: Não foi encontrado nenhum Local de Espera com o id informado!!!");
				return response;
			}
			//criar historico
			HistoricoTransporte ht = new HistoricoTransporte();
			ht.setAcao("Excluído Local de Espera "+v.getLocal());
			ht.setDataHora(LocalDateTime.now());
			ht.setId(null);
			ht.setUsuario(buscarUsuario());
			repo.delete(v);
			response.setDados("Local de Espera deletado com sucesso!!!");
			repoHist.save(ht);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	 }
	
	
	

   
	private String buscarUsuario() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();        
		Object details = authentication.getDetails();        
		if ( details instanceof OAuth2AuthenticationDetails ){
		    OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails)details;

		    @SuppressWarnings("unchecked")
			Map<String, Object> decodedDetails = (Map<String, Object>)oAuth2AuthenticationDetails.getDecodedDetails();

		        String username  = (String) decodedDetails.get("user_name");
		        
		        System.out.println(username);
		    return username;
		}  
		return null;
	} //fecha buscarUsuario

	
	

}//fecha classe
