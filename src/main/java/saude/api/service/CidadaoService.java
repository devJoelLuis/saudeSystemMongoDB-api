package saude.api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

import saude.api.dto.transporteDto.FaltaTransporteDto;
import saude.api.entity.Cidadao;
import saude.api.entity.Falta;
import saude.api.entity.HistoricoCidadao;
import saude.api.entity.Ubs;
import saude.api.entity.Usuario;
import saude.api.entity.transporte.FaltaTransporte;
import saude.api.repository.CidadaoRepository;
import saude.api.repository.HistoricoCidadaoRepository;
import saude.api.repository.UbsRespository;
import saude.api.repository.UsuarioRepository;
import saude.api.response.Response;

@Service
public class CidadaoService {

	@Autowired
	private CidadaoRepository repo;
	
	@Autowired
	private UbsRespository repoUbs;
	
	@Autowired
	private HistoricoCidadaoRepository repoHistCidadao;
	
	@Autowired
	private UsuarioRepository repoUsuario;
	
	

	public Page<Cidadao> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		return repo.findAll(pageable);
	}
	
	
	
	
	

	public Response<Cidadao> findCidadao(String id, String prontuario, String cartaosus) {
		Response<Cidadao> resposta = new Response<Cidadao>();
		Cidadao ret;
		Cidadao cid = null;
		try {
			if (id.equals("") || id == null) {
				if (cartaosus.equals("") || cartaosus == null) {
					// converter para int num prontuario
					Integer numProntuario = Integer.parseInt(prontuario);
					if (numProntuario > 0) {
						cid = repo.findByNumeroProntuario(numProntuario);
						if (cid != null) {
							resposta.setDados(cid);
							return resposta;
						} else {
							resposta.getErros().add("Cidadão não encontrado com o número de prontuário passado");
							return resposta;
						}
					}
				} else {
					cid = repo.findByCartaosus(cartaosus);
					if (cid == null) {
						resposta.getErros().add("Cidadão não encontrado com o número do cartão sus passado");
						return resposta;
					} else {
						resposta.setDados(cid);
						return resposta;
					}
				}
			} else {
				// id foi passado no paramentro então procurar por id

				ret = repo.findOne(id);

				if (ret != null) {
					resposta.setDados(ret);
				} else {
					resposta.getErros().add("Id não encontrado");
				}

			}
		} catch (Exception e) {
			resposta.getErros().add(e.getMessage());
		}
		return resposta;

	}
	
	
	
	
	

	public Response<Page<Cidadao>> findByNome(String nome, int pages, int size) {
		Response<Page<Cidadao>> resposta = new Response<Page<Cidadao>>();
		if (nome.equals("") || nome == null) {
			resposta.getErros().add("Nome não foi informado corretamente");
			return resposta;
		} else {
			if (nome.length() < 3) {
				resposta.getErros().add("O Nome deve conter no mínimo 4 dígitos");
				return resposta;
			} else {
				try {
					Pageable pageable = new PageRequest(pages, size);
					Page<Cidadao> ret = repo.findByNomeContainsIgnoreCaseOrderByNomeAsc(pageable, nome);
					resposta.setDados(ret);
					return resposta;
				} catch (Exception e) {
					resposta.getErros().add(e.getMessage());
					return resposta;
				}
			}
		}

	}
	
	
	
	
	

	public Response<Cidadao> cadastrarCidadao(Cidadao cidadao) {
		Response<Cidadao> response = new Response<>();
		try {
			String username = buscarUsuario();
			Optional<Usuario> userOp = repoUsuario.findByUsuarioIgnoreCase(username);
			Usuario user = userOp.get();
			String validarCidadao = validarCidadao(cidadao, username);
			
			if (!validarCidadao.equals("ok")) {
				response.getErros().add(validarCidadao);
				return response;
			}
			cidadao.setDataCadastro(LocalDateTime.now());
			int numProntuario = gerarNumeroProntuario();
			if(numProntuario == 0) {
				//verificar se existe registro no bando de dados
				Long countTodosRegistros = repo.count();
				if(countTodosRegistros == 0) { //se não exite nenhum registro no banco o número de prontuário será 1
					numProntuario = 1;
				} else {
					response.getErros().add("Erro: Não foi possível gerar um novo número de prontuário!!");
					return response;
				}
				
			}
			//buscar a ubs do cidadao
			String idubs = cidadao.getUbs().getId();
			Ubs ubs = repoUbs.findOne(idubs);
			
			if(ubs == null ) {
				response.getErros().add("Erro: Não foi possível encontrar a ubs com o id passado");
				return response;
			}
			cidadao.setUbs(ubs);
			cidadao.setNumeroProntuario(numProntuario);
			cidadao.setCadastrador(user.getNome());
			cidadao.setId(null);
			Cidadao cidSalvo = repo.save(cidadao);
			response.setDados(cidSalvo);
			return response;

		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}

	
	
	//gera um número de prontuário
	private Integer gerarNumeroProntuario() {
		int retorno = 0;
		try {
			//buscar o ultimo número de prontuário cadastrado
			//criar um pageable para retornar apenas o numero maior
			Cidadao cid = repo.findFirstByOrderByNumeroProntuarioDesc();
			
			retorno = cid.getNumeroProntuario() +1;
			//confirmar se num prontuario não exite
			Long confirmar = repo.countByNumeroProntuario(retorno);
			if(confirmar > 0) {
				return 0;
			}
			return retorno;
		} catch (Exception e) {
          return 0;
		}
		
	}
	
	
	
	

	// validar novo cidadao
	private String validarCidadao(Cidadao cidadao, String username) {
		try {
			if(username == null || username.equals("")) {
				return "Não foi possível obter o username do usuário logado";
			}
			if (cidadao.getNome().equals("") || cidadao.getNome() == null || cidadao.getNome().length() < 5) {
				return "O nome do cidadão não foi informado corretamente";
			}
			if (cidadao.getNascimento() == null) {
				return "A data de nascimento não foi informada corretamente";
			}

			if (cidadao.getNascimento().isAfter(LocalDate.now()) || cidadao.getNascimento().equals(LocalDate.now())) {
				return "A data de nascimento não pode ser maior ou igual que a data atual";
			}

			if (cidadao.getUbs().getId() == null || cidadao.getUbs().getId().equals("")) {
				return "Erro: id da ubs não informado";
			}
			
			// verificar se nome não é duplicado
			Long nomeCidadao = repo.countByNomeIgnoreCase(cidadao.getNome());
			if (nomeCidadao > 0) {
				Cidadao cid = repo.findByNomeIgnoreCase(cidadao.getNome());
				return "O nome digitado já está sendo utilizado no prontuário de número "+ cid.getNumeroProntuario();
			}
			
			//verificar se foi definido um numero de cartao cidadao
			if(cidadao.getCartaocid() != null) {
				if(cidadao.getCartaocid().length() > 2) {
					//verificar se cartão do cidadao não é duplicado
					Long cartaoCid = repo.countByCartaocid(cidadao.getCartaocid());
					if(cartaoCid > 0) {
						return "O número do cartão cidadão fornecido já se encontra cadastrado no banco de dados!!!";
					}
				}
			}
			
			//verificar se numero de rg foi informado
			if(cidadao.getRgNumero() != null) {
				if(cidadao.getRgNumero().length()>5) {
					//verificar se número de rg já se encontra cadastrado
					Long numRg = repo.countByRgNumero(cidadao.getRgNumero());
					if(numRg > 0) {
						//recuperar o nome da pessoa que possui o rg vinculado
						Cidadao cid = repo.findByRgNumero(cidadao.getRgNumero());
						return "O número de RG passado já está vinculado ao cidadão "
								+cid.getNome()+" de prontuário número "+cid.getNumeroProntuario();
					}
				} 
			}
			
			if(cidadao.getCartaosus() == null) {
				return "O número de RG passado já está vinculado ao cidadão ";
			} 
			if(cidadao.getCartaosus().length() < 7) {
				return "O número do cartão sus não foi informado corretamente, a quantidade de caracter está incorreta!";
			}
			Long cartaoSus = repo.countByCartaosus(cidadao.getCartaosus());
			if(cartaoSus > 0) {
				Cidadao cid = repo.findByCartaosus(cidadao.getCartaosus());
				return "O número do cartão sus "+ cidadao.getCartaosus() +
						" já está vinculado ao cidadão "+cid.getNome() + 
						", do prontuário número "+cid.getNumeroProntuario();
			}
			
			if(cidadao.getCpf() != null && !cidadao.getCpf().equals("")) {
				if(cidadao.getCpf().length() < 5 || cidadao.getCpf().length() > 25) {
					return "O número de cpf não é válido";
				}
				Long cpf = repo.countByCpf(cidadao.getCpf());
				if(cpf > 0) {
					Cidadao cid = repo.findByCpf(cidadao.getCpf());
					return "O número do cpf "+ cidadao.getCpf() +
							" já está vinculado ao cidadão "+cid.getNome() + 
							", do prontuário número "+cid.getNumeroProntuario(); 
				}
			}
			
			//veririficar se username exite
			Long usernameCount = repoUsuario.countByUsuarioIgnoreCase(username);
			if(usernameCount == 0) {
				return "Não foi possível encontrar o usuário com o username obtido!!";
			}
				

			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	
	
	
	
	
	public Response<Cidadao> alterarCidadao(Cidadao cidadao) {
		Response<Cidadao> response = new Response<>();
		try {
			String username = buscarUsuario();
			String validacao = validarParaAlteracao(cidadao, username);
			if(!validacao.equals("ok")) {
				response.getErros().add(validacao);
				return response;
			}
			//buscar o cidadao correto
			Cidadao cid= repo.findOne(cidadao.getId());
			if(cid == null) {
				response.getErros().add("Ocorreu um erro ao tentar carregar o cidadão com o número do id passado");
				return response;
			}
			
			//verificar se o numero de prontuario nao foi alterado
			 int numAtual = cid.getNumeroProntuario();
			 int numPassado = cidadao.getNumeroProntuario();
			if(numAtual != numPassado) {
				response.getErros().add("Erro: O número de prontuário não pode ser alterado!!");
				return response;
			}
			//gerar historico de alteração
			Optional<Usuario> userOp= repoUsuario.findByUsuarioIgnoreCase(username);
			Usuario user = userOp.get(); 
			if(user == null) {
				response.getErros().add("Erro: não foi possível encontrar o usuário com o id passado");
				return response;
			}
			cidadao.setUltimaAtualizacao(LocalDateTime.now());
			HistoricoCidadao hc = new HistoricoCidadao(cid, "alteração no cadastro do cidadão", user);
			repoHistCidadao.save(hc);
			Cidadao cidSalvo = repo.save(cidadao);
			response.setDados(cidSalvo);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}

	
	
	
	
	
	private String validarParaAlteracao(Cidadao cidadao, String username) {
		try {
			if(username == null || username.equals("")) {
				return "Não foi passado o username do usuário corretamente";
			}
			
			if (cidadao.getNome().equals("") || cidadao.getNome() == null || cidadao.getNome().length() < 5) {
				return "O nome do cidadão não foi informado corretamente";
			}
			if (cidadao.getNascimento() == null) {
				return "A data de nascimento não foi informada corretamente";
			}

			if (cidadao.getNascimento().isAfter(LocalDate.now()) || cidadao.getNascimento().equals(LocalDate.now())) {
				return "A data de nascimento não pode ser maior ou igual que a data atual";
			}

			if (cidadao.getUbs().getId() == null || cidadao.getUbs().getId().equals("")) {
				return "Erro: id da ubs não informado";
			}
			
			//verificar existe um cidadao no banco com o id passado
			  Long idcidadaoTest = repo.countById(cidadao.getId());
			  if(idcidadaoTest == 0) {
				  return "Erro: não foi encontrado um cidadão com o id passado";
			  }
			
			// verificar se nome não é duplicado
			Long nomeCidadao = repo.countByNomeIgnoreCaseAndIdNot(cidadao.getNome(), cidadao.getId());
			if (nomeCidadao > 0) {
				Cidadao cid = repo.findByNomeIgnoreCase(cidadao.getNome());
				return "O nome digitado já está sendo utilizado no prontuário de número "+cid.getNumeroProntuario();
			}
			
			//verificar se foi definido um numero de cartao cidadao
			if(cidadao.getCartaocid() != null) {
				if(cidadao.getCartaocid().length() > 2) {
					//verificar se cartão do cidadao não é duplicado
					Long cartaoCid = repo.countByCartaocidAndIdNot(cidadao.getCartaocid(), cidadao.getId());
					if(cartaoCid > 0) {
						return "O número do cartão cidadão fornecido já se encontra cadastrado no banco de dados!!!";
					}
				}
			}
			
			//verificar se numero de rg foi informado
			if(cidadao.getRgNumero() != null) {
				if(cidadao.getRgNumero().length()>5) {
					//verificar se número de rg já se encontra cadastrado
					Long numRg = repo.countByRgNumeroAndIdNot(cidadao.getRgNumero(), cidadao.getId());
					if(numRg > 0) {
						//recuperar o nome da pessoa que possui o rg vinculado
						Cidadao cid = repo.findByRgNumero(cidadao.getRgNumero());
						return "O número de RG passado já está vinculado ao cidadão "
								+cid.getNome()+" de prontuário número "+cid.getNumeroProntuario();
					}
				} 
			}
			
			if(cidadao.getCartaosus() == null) {
				return "O número de RG passado já está vinculado ao cidadão ";
			} 
			if(cidadao.getCartaosus().length() < 7) {
				return "O número do cartão sus não foi informado corretamente, a quantidade de caracter está incorreta!";
			}
			Long cartaoSus = repo.countByCartaosusAndIdNot(cidadao.getCartaosus(), cidadao.getId());
			if(cartaoSus > 0) {
				Cidadao cid = repo.findByCartaosus(cidadao.getCartaosus());
				return "O número do cartão sus "+ cidadao.getCartaosus() +
						" já está vinculado ao cidadão "+cid.getNome() + 
						", do prontuário número "+cid.getNumeroProntuario();
			}
			
			if(cidadao.getCpf() != null && !cidadao.getCpf().equals("")) {
				if(cidadao.getCpf().length() < 5 || cidadao.getCpf().length() > 25) {
					return "O número de cpf não é válido";
				}
				Long cpf = repo.countByCpfAndIdNot(cidadao.getCpf(), cidadao.getId());
				if(cpf > 0) {
					Cidadao cid = repo.findByCpf(cidadao.getCpf());
					return "O número do cpf "+ cidadao.getCpf() +
							" já está vinculado ao cidadão "+cid.getNome() + 
							", do prontuário número "+cid.getNumeroProntuario(); 
				}
			}
			
			
			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	
	
	public Response<Cidadao> adicionarFalta(Falta falta, String idcidadao) {
		Response<Cidadao> response = new Response<>();
		try {
			String userUsername= buscarUsuario(); 
			String validar = validarDadosAdicionarFalta(falta, idcidadao, userUsername);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//buscar o usuario
			Optional<Usuario> userOp = repoUsuario.findByUsuarioIgnoreCase(userUsername);
			Usuario user = userOp.get();
			//buscar o cidado
			Cidadao cid = repo.findOne(idcidadao);
			//adicionar a falta ao cidadao
			
			 //pegar a lista de falta
			 List<Falta> faltaLista = cid.getDatasFaltas();
			 List<Falta> novaLista = new ArrayList<>();
			 if(faltaLista != null) {
			 for (Falta ft : faltaLista) {
				if(ft.getData().isAfter(LocalDateTime.now().minusYears(1))) {
					//se a data esta dentro de no mínimo 1 ano
					novaLista.add(ft);
				}
			}
			 }
			 novaLista.add(falta);
			 cid.setDatasFaltas(novaLista);
			 //montar historico 
			 HistoricoCidadao hcid = new HistoricoCidadao(cid, 
					 "adicionado falta local: "+falta.getLocal()+", data da falta:" 
					 + falta.getData(), user);
			 Cidadao cidSalvo = repo.save(cid);
			 repoHistCidadao.save(hcid);
			 response.setDados(cidSalvo);
			 return response;
		} catch (Exception e) {
		  response.getErros().add(e.getMessage());
		  return response;
		}
	}
	
	
	
	

	private String validarDadosAdicionarFalta(Falta falta, String idcidadao, String username) {
		try {
			if(falta.getData() == null) {
				return "A data da falta não foi informada";
			}
			if(falta.getData().isBefore(LocalDateTime.now().minusMonths(2))) {
				return "A data da falta não pode ser inferiror a dois meses";
			}
			if(falta.getLocal() == null || falta.getLocal().contentEquals("")) {
				return "O local da falta não foi informado";
			}
			if(idcidadao == null || idcidadao.equals("")) {
				return "O id do cidadão não foi informado";
			}
			//verificar se existe um cidadão com o id passado
			 Long validarCidadao = repo.countById(idcidadao);
			 if(validarCidadao == 0) {
				 return "Não foi encontrado nenhum cidadão com o id "+ idcidadao;
			 }
			 //verificar iduser
			 if(username == null || username.equals("")) {
				 return "O username do usuário não foi informado";
			 }
			 Long validarUsuario = repoUsuario.countByUsuarioIgnoreCase(username);
			 if(validarUsuario == 0) {
				 return "Não foi encontrado nenhum usuário com o username "+ username;
			 }
			
			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	
	
	
	
	public Response<Cidadao> liberarCidadao(String data, String idcidadao) {
		Response<Cidadao> response = new Response<>();
		try {
			String userUsuario = buscarUsuario(); 
			String validar = validarDadosLibercao(data, idcidadao, userUsuario);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//transformar a data string em localdate
			LocalDate dataLiberacao = LocalDate.parse(data);
			//buscar o cidadao
			Cidadao cid = repo.findOne(idcidadao);
			//buscar o usuario que está fazendo a operação
			Optional<Usuario> userOp = repoUsuario.findByUsuarioIgnoreCase(userUsuario);
			Usuario user = userOp.get();
			//pegar a lista de data
			List<LocalDate> listaLiberacao = cid.getDatasDesbloqueio();
			if(listaLiberacao.contains(dataLiberacao)) {
				response.getErros().add("O cidadão já possui uma data de liberação para o dia "+ dataLiberacao);
				return response;
			}
			List<LocalDate> novaLista = new ArrayList<>();
			//pecorrer a lista e excluir as datas inferior a 3 meses
			if(listaLiberacao != null) {
			for (LocalDate dt : listaLiberacao) {
				if(dt.isAfter(LocalDate.now().minusMonths(6))) {
					novaLista.add(dt);
				}
			}
			}
			novaLista.add(dataLiberacao);
			//criar historico
			HistoricoCidadao hcid = new HistoricoCidadao(cid, 
					"Liberado Cidadao, data liberacao: "+ data, user);
						
			cid.setDatasDesbloqueio(novaLista);
			Cidadao cidSalvo = repo.save(cid);
			repoHistCidadao.save(hcid);
			response.setDados(cidSalvo);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}

	
	
	
	
	
	
	private String validarDadosLibercao(String data, String idcidadao, String username) {
		try {
			if(data == null || data.equals("")) {
				return "A data da liberacao não foi informada";
			}
			//tranforma a string em LocalDate
			LocalDate dataTeste = LocalDate.parse(data);
			/*
			if(dataTeste.isBefore(LocalDate.now())) {
				return "A data da liberacao não pode ser inferior a data atual";
			}
			*/
			if(idcidadao == null || idcidadao.equals("")) {
				return "O id do cidadão não foi informado";
			}
			//verificar se existe um cidadão com o id passado
			 Long validarCidadao = repo.countById(idcidadao);
			 if(validarCidadao == 0) {
				 return "Não foi encontrado nenhum cidadão com o id "+ idcidadao;
			 }
			 //verificar iduser
			 if(username == null || username.equals("")) {
				 return "O username do usuário não foi informado";
			 }
			 Long validarUsuario = repoUsuario.countByUsuarioIgnoreCase(username);
			 if(validarUsuario == 0) {
				 return "Não foi encontrado nenhum usuário com o username "+ username ;
			 }
			
			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	
	
	
	//definir falta para o cidadao
	public Response<Cidadao> faltaTransporte(FaltaTransporteDto ft) {
		Response<Cidadao> response = new Response<>();
		try {
			String validar = validarFalta(ft);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			Cidadao cid = repo.findOne(ft.getIdcidadao());
			if(cid == null) {
				response.getErros().add("Erro: Não foi possível definir a falta no transporte para o cidadão "
						+ "porque não foi encontrado nenhum cidadão com o id "+ ft.getIdcidadao());
				return response;
			}
			//criar historico
			Optional<Usuario> userOp = repoUsuario.findByUsuarioIgnoreCase(buscarUsuario());
			HistoricoCidadao hc = new HistoricoCidadao(cid, "Adicionado falta no transporte: dia da falta "+ ft.getData()+ 
					" Local da falta "+ft.getLocalDestino(), userOp.get());
			cid.getFaltasTranporte().add(new FaltaTransporte(ft));
			Cidadao cidSalvo = repo.save(cid);
			response.setDados(cidSalvo);
			repoHistCidadao.save(hc);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}







	private String validarFalta(FaltaTransporteDto ft) {
		if(ft.getIdcidadao() == null || ft.getIdcidadao().equals("")) {
			return "Erro: não foi passado o id do cidadão que faltou no transporte";
		}
		if(ft.getData() == null) {
			return "Erro: não foi passado a data da falta do cidadão no transporte";
		}
		if(ft.getLocalDestino() == null || ft.getLocalDestino().equals("")) {
			return "Erro: não foi fornecido o local de destino da falta do cidadão";
		}
		return "ok";
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




	
	


	public Response<Page<Cidadao>> findByParamAll(String param, int page, int size) {
		Response<Page<Cidadao>> response = new Response<>();
		if(param == null || param.equals("")) {
			response.getErros().add("Não foi passado nenhum parâmetro para a pesquisar o cidadão");
			return response;
		}
		Page<Cidadao>cidPage = null;
		Cidadao cidadao = null;
		Pageable pageable = new PageRequest(page, size);
		try {
			//tentar converter para numero
			try {
				int numProntuario = Integer.parseInt(param);
				cidadao = repo.findByNumeroProntuario(numProntuario);
				if(cidadao == null) {
					response.getErros().add("Não foi possível encontrar um cidadão com o número de prontuário informado");
					return response;
				}
				
				List<Cidadao>cidList = new ArrayList<>();
				cidList.add(cidadao);
				cidPage = new PageImpl<>(cidList, pageable, cidList.size());
				response.setDados(cidPage);
				return response;
			} catch (Exception e) {
			}
			
			//se chegar aqui significa que não foi encontrado nenhum numero de prontuário então procurar pelo numero do cartao sus
			cidadao = repo.findByCartaosus(param);
			if(cidadao != null) {
				List<Cidadao>cidList = new ArrayList<>();
				cidList.add(cidadao);
				cidPage = new PageImpl<>(cidList, pageable, cidList.size());
				response.setDados(cidPage);
				return response;
			}
			
			//procurar por parte do nome
			cidPage = repo.findByNomeContainsIgnoreCaseOrderByNomeAsc(pageable, param);
			response.setDados(cidPage);
			return response;
			
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}






	public Response<List<String>> findByNomeQuery(String nome, int page, int size) {
		Response<List<String>> response = new Response<>();
		try {
			if(nome.length() <= 1) {
				response.getErros().add("Digite um nome contendo no mínimo dois caracteres para consultar");
				return response;
			}
			//criar um pageable para retornar somete a quantidade de registro definida no size
			Pageable pageable = new PageRequest(page, size);
			//pegar o retorno encontrado em uma lista de cidadaos
			List<Cidadao> cidadaos = repo.findByNomeLikeIgnoreCaseOrderByNomeAsc(nome, pageable); 
			//criar uma lista de nomes vazia
			List<String> nomes = new ArrayList<>();
			for (Cidadao cid : cidadaos) {
				nomes.add(cid.getNome());
			}
			response.setDados(nomes);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}






	public Response<Cidadao> findByNomeCompleto(String nome) {
		 Response<Cidadao> response = new Response<>();
		 if(nome.length() <= 1) {
			 response.getErros().add("Erro: Não exite nome de cidadão com menos de 2 caracters no banco de dados");
			 return response;
		 } 
		 
		 try {
			Cidadao cid = repo.findByNomeIgnoreCase(nome);
			if(cid == null) {
				response.getErros().add("Erro:Não foi possível carregar o cidadão com o nome "+ nome);
				return response;
			}
			response.setDados(cid);
			return response;
		} catch (Exception e) {
		  response.getErros().add(e.getMessage());
		  return response;
		}
	}






	public Response<Cidadao> liberarCidadaoTransporte(String data, String idcidadao) {
		Response<Cidadao> response = new Response<>();
		try {
			String userUsuario = buscarUsuario(); 
			String validar = validarDadosLibercao(data, idcidadao, userUsuario);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//transformar a data string em localdate
			LocalDate dataLiberacao = LocalDate.parse(data);
			//buscar o cidadao
			Cidadao cid = repo.findOne(idcidadao);
			//buscar o usuario que está fazendo a operação
			Optional<Usuario> userOp = repoUsuario.findByUsuarioIgnoreCase(userUsuario);
			Usuario user = userOp.get();
			//pegar a lista de data
			List<LocalDate> listaLiberacaoTransporte = cid.getDatasDesbloqueioTransporte();
			if(listaLiberacaoTransporte.contains(dataLiberacao)) {
				response.getErros().add("O cidadão já possui uma data de liberação para o dia "+ dataLiberacao);
				return response;
			}
			List<LocalDate> novaLista = new ArrayList<>();
			//pecorrer a lista e excluir as datas inferior a 3 meses
			if(listaLiberacaoTransporte != null) {
			for (LocalDate dt : listaLiberacaoTransporte) {
				if(dt.isAfter(LocalDate.now().minusMonths(6))) {
					novaLista.add(dt);
				}
			}
			}
			novaLista.add(dataLiberacao);
			//criar historico
			HistoricoCidadao hcid = new HistoricoCidadao(cid, 
					"Desbloqueado o transporte do Cidadao, data do desbloqueio: "+ data, user);
						
			cid.setDatasDesbloqueioTransporte(novaLista);
			Cidadao cidSalvo = repo.save(cid);
			repoHistCidadao.save(hcid);
			response.setDados(cidSalvo);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}






	public Response<Cidadao> findByCartaoCidadao(String ccid) {
		Response<Cidadao> response = new Response<>();
		if(ccid.length() < 2) {
			response.getErros().add("Erro: Não foi possível encontrar um cidadão com o cartão de número "+ ccid);
			return response;
		}
		try {
			
			Cidadao cidadao = repo.findByCartaocid(ccid);
			if(cidadao == null) {
				response.getErros().add("Erro: Não foi possível encontrar um cidadão com o cartão de número "+ ccid);
				return response;
			}
			response.setDados(cidadao);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}






	public Response<Cidadao> findByNumeroProntuario(String num) {
		Response<Cidadao> response = new Response<>();
		if(num.length() < 2) {
			response.getErros().add("Erro: Não foi possível encontrar um cidadão com o número de prontuário igual a "+ num);
			return response;
		}
		try {
			
			Cidadao cidadao = repo.findByNumeroProntuario(Integer.parseInt(num));
			if(cidadao == null) {
				response.getErros().add("Erro: Não foi possível encontrar um cidadão com o número de prontuário igual a "+ num);
				return response;
			}
			response.setDados(cidadao);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}






	public Response<Cidadao> findByNumeroCartaoSus(String num) {
		Response<Cidadao> response = new Response<>();
		if(num.length() < 2) {
			response.getErros().add("Erro: Não foi possível encontrar um cidadão com o número do cartão sus igual a "+ num);
			return response;
		}
		try {
			
			Cidadao cidadao = repo.findByCartaosus(num);
			if(cidadao == null) {
				response.getErros().add("Erro: Não foi possível encontrar um cidadão com o número do cartao sus igual a "+ num);
				return response;
			}
			response.setDados(cidadao);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}

	
	
	
	

}
