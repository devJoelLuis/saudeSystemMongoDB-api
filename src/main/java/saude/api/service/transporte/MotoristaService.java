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
import saude.api.entity.transporte.Motorista;
import saude.api.repository.transporte.HistoricoTransporteRepository;
import saude.api.repository.transporte.MotoristaRepository;
import saude.api.response.Response;

@Service
public class MotoristaService {
	
	@Autowired
	private MotoristaRepository repo;
	
	
	@Autowired
	private HistoricoTransporteRepository repoHist;
	
	
	
	//busca todos os motoristas
	public Response<Page<Motorista>> buscarTodosMotoristas(int page, int size) {
		Response<Page<Motorista>> response = new Response<>();
		try {
			
			Pageable pageable = new PageRequest(page, size);
			Page<Motorista> motoristasPage = repo.findByOrderByNomeMotoristaAsc(pageable);
			response.setDados(motoristasPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
		
	}
	
	
	//busca por id do motorista
	public Response<Motorista> buscarPorId(String id) {
		Response<Motorista> response = new Response<>();
		try {
			if(id == null || id.equals("")) {
				response.getErros().add("Erro: o id do motorista não foi informado!!");
				return response;
			}
			
			Motorista motorista = repo.findOne(id);
			if(motorista == null) {
				response.getErros().add("Não foi encontrado nenhum motorista com o id informado!!!");
				return response;
			}
			
			
			response.setDados(motorista);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	//buscar motorista por parte do nome
	
	public Response<Page<Motorista>> buscarPorNome(String nome, int page, int size) {
		Response<Page<Motorista>> response = new Response<>();
		try {
			Page<Motorista> motoristasPage;
			Pageable pageable = new PageRequest(page, size);
			if(nome == null || nome.equals("")) {
				motoristasPage = repo.findAll(pageable);
				response.setDados(motoristasPage);
				return response;
			}
			motoristasPage = repo.findByNomeMotoristaLikeIgnoreCaseOrderByNomeMotorista(nome, pageable);
			response.setDados(motoristasPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	
	//cadastrar novo motorista
	public Response<Motorista> cadastrarNovoMotorista(Motorista m) {
		Response<Motorista> response = new Response<>();
		try {
			String validar = validarParaCadastro(m);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//confirmar que nenhum id está sendo passado
			m.setId(null);
			//cria um novo historico
			HistoricoTransporte ht = new HistoricoTransporte();
			ht.setAcao("Cadastrado novo Motorista "+m.getNomeMotorista());
			ht.setDataHora(LocalDateTime.now());
			ht.setId(null);
			ht.setUsuario(buscarUsuario());
			Motorista vSalvo = repo.save(m);
			response.setDados(vSalvo);
			repoHist.save(ht);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	
	
	
	private String validarParaCadastro(Motorista m) {
		try {
			
			if(m.getNomeMotorista() == null || m.getNomeMotorista().equals("")) {
				return "Erro: Nome não do monotrista não foi informado";
			}
			if(m.getCartaVencimento() == null) {
				return "Erro: data do vencimento da carta não informado";
			}
			Long countRegistro = repo.countByNomeMotoristaIgnoreCase(m.getNomeMotorista());
			if(countRegistro > 0) {
			   return "Erro: O nome "+m.getNomeMotorista()+" já se encontra cadastrado no sistema";
			}
			return "ok";
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
	
	
	//Alterar motorista
		public Response<Motorista> AlterarMotorista(Motorista v) {
			Response<Motorista> response = new Response<>();
			try {
				String validar = validarParaAlteracao(v);
				if(!validar.equals("ok")) {
					response.getErros().add(validar);
					return response;
				}
				//criar historico
				HistoricoTransporte ht = new HistoricoTransporte();
				ht.setAcao("Alterado motorista "+v.getNomeMotorista());
				ht.setDataHora(LocalDateTime.now());
				ht.setId(null);
				ht.setUsuario(buscarUsuario());
				//buscar MOtorista atual
				Motorista mAtual = repo.findOne(v.getId());
				if(mAtual == null) {
					response.getErros().add("Não foi possível encontrar um motorista com o valor do id passado!!");
					return response;
				}
				mAtual = v;
				Motorista vSalvo = repo.save(mAtual);
				response.setDados(vSalvo);
				repoHist.save(ht);
				return response;
			} catch (Exception e) {
				response.getErros().add(e.getMessage());
				return response;
			}
		}
	
	
	
	
	


	private String validarParaAlteracao(Motorista m) {
		try {
			if(m.getId() == null || m.getId().equals("")) {
				return "Erro: O id do motorista não foi informado!!";
			}
			if(m.getCartaVencimento() == null) {
				return "Erro: Data de vencimento da carta não foi informado";
			}
			Long countId = repo.countById(m.getId());
			if(countId == 0) {
				return "Erro: Não foi possível encontrar um motorista com o id informador!!!";
			}
			Long countRegistro = repo.countByNomeMotoristaIgnoreCaseAndIdNot(m.getNomeMotorista(), m.getId());
			if(countRegistro > 0) {
			   return "Erro: O nome "+m.getNomeMotorista()+" já se encontra cadastrado no sistema";
			}
			return "ok";
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
	
	
	
	 // deletar motoristas
	 public Response<String> deletarMotorista(String id) {
		 Response<String> response = new Response<>();
		 try {
			if(id == null || id.equals("")) {
				response.getErros().add("Erro: O id do motorista a ser deletado não foi informado!!!");
				return response;
			}
			Motorista m = repo.findOne(id);
			if(m == null) {
				response.getErros().add("Erro: Não foi encontrado nenhum motorista com o id informado!!!");
				return response;
			}
			//criar historico
			HistoricoTransporte ht = new HistoricoTransporte();
			ht.setAcao("Excluído Motorista "+m.getNomeMotorista());
			ht.setDataHora(LocalDateTime.now());
			ht.setId(null);
			ht.setUsuario(buscarUsuario());
			repo.delete(m);
			response.setDados("Motorista deletado com sucesso!!!");
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
