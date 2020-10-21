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
import saude.api.entity.transporte.Veiculo;
import saude.api.repository.transporte.HistoricoTransporteRepository;
import saude.api.repository.transporte.VeiculoRepository;
import saude.api.response.Response;

@Service
public class VeiculoService {
	
	@Autowired
	private VeiculoRepository repo;
	
	
	@Autowired
	private HistoricoTransporteRepository repoHist;
	
	
	
	//busca todos os veiculos
	public Response<Page<Veiculo>> buscarTodosVeiculos(int page, int size) {
		Response<Page<Veiculo>> response = new Response<>();
		try {
			
			Pageable pageable = new PageRequest(page, size);
			Page<Veiculo> veiculosPage = repo.findAll(pageable);
			response.setDados(veiculosPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
		
	}
	
	
	//busca por id do veiculo
	public Response<Veiculo> buscarPorId(String id) {
		Response<Veiculo> response = new Response<>();
		try {
			if(id == null || id.equals("")) {
				response.getErros().add("Erro: o id do veículo não foi informado!!");
				return response;
			}
			
			Veiculo veiculo = repo.findOne(id);
			if(veiculo == null) {
				response.getErros().add("Não foi encontrado nenhum veículo com o id informado!!!");
				return response;
			}
			
			
			response.setDados(veiculo);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	//buscar veiculo por parte do nome
	
	public Response<Page<Veiculo>> buscarPorNome(String nome, int page, int size) {
		Response<Page<Veiculo>> response = new Response<>();
		try {
			Page<Veiculo> veiculosPage;
			Pageable pageable = new PageRequest(page, size);
			if(nome == null || nome.equals("")) {
				veiculosPage = repo.findAll(pageable);
				response.setDados(veiculosPage);
				return response;
			}
			
			veiculosPage = repo.findByVeiculoIgnoreCase(nome, pageable);
			response.setDados(veiculosPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	
	//cadastrar novo veiculo
	public Response<Veiculo> cadastrarNovoVeiculo(Veiculo v) {
		Response<Veiculo> response = new Response<>();
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
			ht.setAcao("Cadastrado novo Veículo "+v.getVeiculo());
			ht.setDataHora(LocalDateTime.now());
			ht.setId(null);
			ht.setUsuario(buscarUsuario());
			Veiculo vSalvo = repo.save(v);
			response.setDados(vSalvo);
			repoHist.save(ht);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	
	
	
	
	
	
	
	
	private String validarParaCadastro(Veiculo v) {
		try {
			if(v.getLugares() < 1) {
				return "Erro: O veículo deve possuir no mínimo um lugar disponível";
			}
			if(v.getVeiculo() == null || v.getVeiculo().equals("")) {
				return "Erro: Nome do veículo não informado";
			}
			if(v.getPlaca() == null || v.getPlaca().equals("")) {
				return "Erro: A placa do veículo não foi informado";
			}
			//verificar se veículo já não esta cadastrado
			Long countRegistro = repo.countByPlacaIgnoreCase(v.getPlaca());
			if(countRegistro > 0) {
				return "Erro: A placa do veículo já está cadastrada no sistema";
			}
			return "ok";
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
	
	
	//Alterar veiculo
		public Response<Veiculo> AlterarVeiculo(Veiculo v) {
			Response<Veiculo> response = new Response<>();
			try {
				String validar = validarParaAlteracao(v);
				if(!validar.equals("ok")) {
					response.getErros().add(validar);
					return response;
				}
				//criar historico
				HistoricoTransporte ht = new HistoricoTransporte();
				ht.setAcao("Alterado Veículo "+v.getVeiculo());
				ht.setDataHora(LocalDateTime.now());
				ht.setId(null);
				ht.setUsuario(buscarUsuario());
				
				//buscar o veículo para alterar
				Veiculo veiculoAtual = repo.findOne(v.getId());
				if(veiculoAtual == null) {
					response.getErros().add("Erro: não foi possível encontrar o veículo com o valor do id passado");
					return response;
				}
				veiculoAtual = v;
				Veiculo vSalvo = repo.save(veiculoAtual);
				response.setDados(vSalvo);
				repoHist.save(ht);
				return response;
			} catch (Exception e) {
				response.getErros().add(e.getMessage());
				return response;
			}
		}
	
	
	
	
	


	private String validarParaAlteracao(Veiculo v) {
		try {
			if(v.getId() == null || v.getId().equals("")) {
				return "Erro: O id do veículo não foi informado!!";
			}
			if(v.getLugares() < 1) {
				return "Erro: O veículo deve possuir no mínimo um lugar disponível";
			}
			if(v.getVeiculo() == null || v.getVeiculo().equals("")) {
				return "Erro: Nome do veículo não informado";
			}
			Long countId = repo.countById(v.getId());
			if(countId == 0) {
				return "Erro: Não foi possível encontrar um veículo com o id informador!!!";
			}
			if(v.getPlaca() == null || v.getPlaca().equals("")) {
				return "Erro: A placa do veículo não foi informado";
			}
			//verificar se veículo já não esta cadastrado
			Long countRegistro = repo.countByPlacaIgnoreCaseAndIdNot(v.getPlaca(), v.getId());
			if(countRegistro > 0) {
				return "Erro: A placa do veículo já está cadastrada no sistema";
			}
			return "ok";
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
	
	
	
	 // deletar veiculos
	 public Response<String> deletarVeiculo(String id) {
		 Response<String> response = new Response<>();
		 try {
			if(id == null || id.equals("")) {
				response.getErros().add("Erro: O id do veículo a ser deletado não foi informado!!!");
				return response;
			}
			Veiculo v = repo.findOne(id);
			if(v == null) {
				response.getErros().add("Erro: Não foi encontrado nenhum veículo com o id informado!!!");
				return response;
			}
			//criar historico
			HistoricoTransporte ht = new HistoricoTransporte();
			ht.setAcao("Excluído Veículo "+v.getVeiculo());
			ht.setDataHora(LocalDateTime.now());
			ht.setId(null);
			ht.setUsuario(buscarUsuario());
			repo.delete(v);
			response.setDados("Veículo deletado com sucesso!!!");
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


	public Response<Page<Veiculo>> buscarPorNomeOuPlaca(String param, int page, int size) {
		Response<Page<Veiculo>> response = new Response<>();
		try {
			Pageable pageable = new PageRequest(page, size);
			Page<Veiculo> vPage = repo.findByVeiculoLikeIgnoreCase(param, pageable);
			if(vPage.getNumberOfElements() == 0) {
				vPage = repo.findByPlacaLikeIgnoreCase(param, pageable);
			}
			response.setDados(vPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}

	
	

}//fecha classe
