package saude.api.service;


import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import saude.api.entity.HistoricoUsuario;
import saude.api.entity.Usuario;
import saude.api.repository.HistoricoUsuarioRepository;
import saude.api.repository.UsuarioRepository;
import saude.api.response.Response;

@SuppressWarnings("deprecation")
@Service
public class UsuarioService {

	 @Autowired
	 private UsuarioRepository repo;
	 
	 @Autowired
	 private HistoricoUsuarioRepository repoHist;
	 
	 
	 
	 //cadastrar novo usuario
	
	public Response<Usuario> cadastrarNovo(Usuario user){
		 Response<Usuario> response = new Response<>();
		 try {
			 String username = buscarUsuario();
			 String validar = validarUsuarioCadastro(user, username);
			 if(!validar.equals("ok")) {
				 response.getErros().add(validar);
				 return response;
			 }
			Optional<Usuario> userOp = repo.findByUsuarioIgnoreCase(username);
			Usuario userAcao = userOp.get();
			//montar historico
			HistoricoUsuario hUser = new HistoricoUsuario(user, "Cadastro de novo usuário no sistema", userAcao);
			//encodar senha
			user.setSenha(new Md5PasswordEncoder().encodePassword(user.getSenha(), null));
			user.setId(null);
			Usuario userSalvo = repo.save(user);
			repoHist.save(hUser);
			response.setDados(userSalvo);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	 }
	 
	 
	 
	 
	
	 
	 
	 
	 
	 private String validarUsuarioCadastro(Usuario user, String username) {
		try {
			//verificar se nome é valido
			if(user.getNome() == null || user.getNome().equals("")) {
				return "O nome completo do usuário não foi fornecido";
			}
			//verificar se o username do novo cadastro é valido
			if(user.getUsuario() == null || user.getUsuario().equals("")) {
				return "O nome de usuário não foi fornecido";
			}
			//verificar se username do usuário que está fazendo o cadastro é valido
			if(username == null || username.equals("")) {
				return "Não foi possível obter o username do usuário logado";
			}
			//verificar se ubs é valido
			if(user.getUbs() == null) {
				return "Não foi fornecido a ubs vinculada ao usuário";
			}
			//verificar se senha é valido
			if(user.getSenha() == null || user.getSenha().length() < 6) {
				return "Senha inválida: número mínimo de seis caracteres";
			}
			//verificar se profile é valido
			if(user.getProfile() == null || !user.getProfile().equals("ROLE_ADMIN")
					&&!user.getProfile().equals("ROLE_USUARIO")) {
				return "O perfil do usuário não foi informado corretamente";
			}
			//verificar se nome é unico
			Long nomeCount = repo.countByNomeIgnoreCase(user.getNome());
			if(nomeCount > 0) {
				return "ERRO: Já existe um outro cadastro com o mesmo nome no sistema";
			}
			
			//verificar se username é unico
			Long usernameCount = repo.countByUsuarioIgnoreCase(user.getUsuario());
			if(usernameCount > 0) {
				return "O nome de usuário fornecido já está sendo utilizado";
			}
			return "ok";
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	 
	 
	 
	 
	 
	//alterar usuario
	
	public Response<Usuario> alterarUsuario(Usuario user){
		 Response<Usuario> response = new Response<>();
		 try {
			 String username = buscarUsuario();
			 String validar = validarUsuarioAlteracao(user, username);
			 if(!validar.equals("ok")) {
				 response.getErros().add(validar);
				 return response;
			 }
			Optional<Usuario> userOp = repo.findByUsuarioIgnoreCase(username);
			Usuario userAcao = userOp.get();
			//montar historico
			HistoricoUsuario hUser = new HistoricoUsuario(user, "Alteração de dados do usuário no sistema", userAcao);
			//encodar senha
			user.setSenha(new Md5PasswordEncoder().encodePassword(user.getSenha(), null));
			Usuario userSalvo = repo.save(user);
			repoHist.save(hUser);
			response.setDados(userSalvo);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	 }
	 
	 private String validarUsuarioAlteracao(Usuario user, String username) {
		 try {
			    //validar id
				if(user.getId() == null || user.getId().equals("")) {
					return "Não foi fornecido o id do usuário a ser alterado";
				}
			    //veriricar se o id exites
				Long idCount = repo.countById(user.getId());
				if(idCount == 0) {
					return "Não foi encontrado nenhum usuário com o id fornecido";
				}
			   //verificar se nome é valido
				if(user.getNome() == null || user.getNome().equals("")) {
					return "O nome completo do usuário não foi fornecido";
				}
				//verificar se o username do novo cadastro é valido
				if(user.getUsuario() == null || user.getUsuario().equals("")) {
					return "O nome de usuário não foi fornecido";
				}
				//verificar se username do usuário que está fazendo o cadastro é valido
				if(username == null || username.equals("")) {
					return "Não foi possível obter o username do usuário logado";
				}
				//verificar se ubs é valido
				if(user.getUbs() == null) {
					return "Não foi fornecido a ubs vinculada ao usuário";
				}
				//verificar se senha é valido
				if(user.getSenha() == null || user.getSenha().length() < 6) {
					return "Senha inválida: número mínimo de seis caracteres";
				}
				//verificar se profile é valido
				if(user.getProfile() == null || !user.getProfile().equals("ROLE_ADMIN")
						&&!user.getProfile().equals("ROLE_USUARIO")) {
					return "O perfil do usuário não foi informado corretamente";
				}
				//verificar se nome é unico
				Long nomeCount = repo.countByNomeIgnoreCaseAndIdNot(user.getNome(), user.getId());
				if(nomeCount > 0) {
					return "ERRO: Já existe um outro cadastro com o mesmo nome no sistema";
				}
				
				//verificar se username é unico
				Long usernameCount = repo.countByIgnoreCaseUsuarioAndIdNot(user.getUsuario(), user.getId());
				if(usernameCount > 0) {
					return "O nome de usuário fornecido já está sendo utilizado";
				}
				return "ok";
				
			} catch (Exception e) {
				return e.getMessage();
			}
	}









	//listar todos
	public Response<Page<Usuario>> listarTodos(int page, int size){
		Response<Page<Usuario>> response = new Response<>();
		try {
			//montar um pageable
			Pageable pageable = new PageRequest(page, size);
			Page<Usuario> usuarios = repo.findAll(pageable);
			response.setDados(usuarios);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	 
	 // buscar por id
	public Response<Usuario> buscarPorId(String iduser){
		Response<Usuario> response = new Response<>();
	   try {
			if(iduser == null || iduser.equals("")) {
				response.getErros().add("O id do usuário não foi fornecido para consulta");
				return response;
			}
			Usuario user = repo.findOne(iduser);
			if(user == null) {
				response.getErros().add("Não foi encontrado nenhum usuário com o id fornecido");
			}
			response.setDados(user);
			return response;
	} catch (Exception e) {
		response.getErros().add(e.getMessage());
		return response;
	}
	}
	 
	 //buscar por parte do nome
	 
	 //alterar usuario
	 
	 
	 
	 
	 
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









	public Response<Page<Usuario>> buscarPorParteNome(String nome, int page, int size) {
		Response<Page<Usuario>> response = new Response<>();
		try {
			if(nome == null || nome.equals("")) {
				response.getErros().add("O nome não foi informado!!!");
				return response;
			}
			Pageable pageable = new PageRequest(page, size);
			Page<Usuario> users = repo.findByNomeLikeIgnoreCase(nome, pageable);
			response.setDados(users);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	 
	 
	 
	 
	 
	 
	 
	 
	
} //fecha classe
