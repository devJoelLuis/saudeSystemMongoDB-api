package saude.api.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import saude.api.entity.HistoricoUbs;
import saude.api.entity.Ubs;
import saude.api.entity.Usuario;
import saude.api.repository.HistoricoUbsRepository;
import saude.api.repository.UbsRespository;
import saude.api.repository.UsuarioRepository;
import saude.api.response.Response;

@Service
public class UbsService {
	
	@Autowired
	UbsRespository repo;
	
	
	@Autowired
	UsuarioRepository repoUser;
	
	@Autowired
	HistoricoUbsRepository repoHist;
	
	public Page<Ubs> findAll(Pageable pages) {
		return repo.findAll(pages);
	}
	
	

	public Response<Ubs> buscarPorId(String idubs) {
		Response<Ubs> response = new Response<>();
		try {
			if(idubs.equals("")||idubs == null) {
				response.getErros().add("O id da ubs não foi informado corretamente");
				return response;
			}
			Ubs ubs = repo.findOne(idubs);
			
			if(ubs == null) {
				response.getErros().add("Não foi encontrado nenhuma Ubs com o id digitado");
				return response;
			}
			response.setDados(ubs);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}

	
	
	public Response<Ubs> alterarUbs(Ubs ubs, String iduser) {
		Response<Ubs> response = new Response<>();
		try {
			String validar = validarUbsAlteracao(ubs, iduser);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//buscar o cidadao que está fazendo a alteração
			Usuario user = repoUser.findOne(iduser);
			if(user == null) {
				response.getErros().add("Não foi possível buscar o usuário com o id "+ iduser);
				return response;
			}
			//criar historico
			HistoricoUbs hubs = new HistoricoUbs(user, "alteração de dados da ubs", ubs);
			Ubs ubsSalva = repo.save(ubs);
			repoHist.save(hubs);
			response.setDados(ubsSalva);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
		
	}
	
	
	//função valida antes da alteração
	private String validarUbsAlteracao(Ubs ubs, String iduser) {
		try {
			if(ubs.getId() == null || ubs.getId().equals("")) {
				return "O id da ubs não foi informado corretamente";
			}
			 Long testeUbs = repo.countById(ubs.getId());
			 if(testeUbs == 0) {
				 return "Não foi encontrado ubs com o id "+ ubs.getId();
			 }
			if(ubs.getNome()==null || ubs.getNome().equals("")) {
				return "O nome da ubs não foi informado";
			}
			//verificar se o nome já se encontra cadastrado no banco
			  Long validarNome = repo.countByNomeIgnoreCaseAndIdNot(ubs.getNome(), ubs.getId());
			  if(validarNome > 0) {
				  return "Erro: O sistema já possui uma UBS com o nome "+ ubs.getNome() + " cadastrado";
			  }
			
			if(ubs.getResponsavel()==null || ubs.getResponsavel().equals("")) {
				return "O nome do responsável pela ubs não foi informado";
			}
			if(iduser == null || iduser.equals("")) {
				return "O id do usuário não foi passado";
			}
			Long testeUser = repoUser.countById(iduser);
			if(testeUser == 0) {
				return "não foi possível encontrar um usuário com o id passado";
			}
			
			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}



	public Response<Ubs> cadastrarUbs(Ubs ubs) {
		Response<Ubs> response = new Response<>();
		try {
			 String username = buscarUsuario();
			 if(username == null) {
				 response.getErros().add("Erro: Não foi possível obter o usuário logado!!");
				 return response;
			 }
			 Optional<Usuario> userLogagoOpt = repoUser.findByUsuarioIgnoreCase(username);
			 if(!userLogagoOpt.isPresent()) {
				 response.getErros().add("Erro: Não foi possível obter o usuário logado!!");
				 return response;
			 }
			 
			String validar = validarNovoCadastroUbs(ubs);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//pegar o usuario que esta fazendo o cadastro
			Usuario user = userLogagoOpt.get();
			
			//setar o id null para garantir
			ubs.setId(null);
			Ubs ubsSalva = repo.save(ubs);
			//gerar um novo historico ubs
			HistoricoUbs hUbs = new HistoricoUbs(user, "Cadastrada nova UBS", ubsSalva);
			response.setDados(ubsSalva);
			repoHist.save(hUbs);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}



	private String validarNovoCadastroUbs(Ubs ubs) {
		try {
			
			if(ubs.getNome()==null || ubs.getNome().equals("")) {
				return "O nome da ubs não foi informado";
			}
			Long validarNome = repo.countByNomeIgnoreCase(ubs.getNome());
			if(validarNome > 0) {
				return "Erro: O sistema já possui uma UBS com o nome "+ ubs.getNome() + " cadastrado";
			}
			
			
			if(ubs.getResponsavel()==null || ubs.getResponsavel().equals("")) {
				return "O nome do responsável pela ubs não foi informado";
			}
			
			
			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
	}



	public Response<String> deletarUbs(String idubs, String iduser) {
		Response<String> response = new Response<>();
		try {
			if(idubs == null || idubs.equals("")) {
				response.getErros().add("Erro: não foi passado o id da ubs");
				return response;
			}
			if(iduser == null || iduser.equals("")) {
				response.getErros().add("O id do usuário não é válido");
			}
			//buscar o usuário que está deletando para fazer o historico
			Usuario user = repoUser.findOne(iduser);
			if(user == null) {
				response.getErros().add("Erro: não foi possível carregar o usuário com o id "+ iduser);
				return response;
			}
			Ubs ubs = repo.findOne(idubs);
			if(ubs == null) {
				response.getErros().add("Erro: não foi possível carregar a ubs com o id "+ idubs);
				return response;
			}
			//criar um historico antes de deletar
			HistoricoUbs hubs = new HistoricoUbs(user, "Deletado a ubs", ubs);
			//remover ubs
			repo.delete(ubs);
			//gravar historico
			repoHist.save(hubs);
			//retornar feedback de ok
			response.setDados("Ubs deletada com sucesso!!!");
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
	}
	

}//fecha class
