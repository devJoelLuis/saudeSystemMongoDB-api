package saude.api.service.transporte;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import saude.api.dto.transporteDto.AgendaDto;
import saude.api.dto.transporteDto.MotoristaDto;
import saude.api.dto.transporteDto.VeiculoDto;
import saude.api.entity.Usuario;
import saude.api.entity.transporte.Agenda;
import saude.api.entity.transporte.BancoVeiculo;
import saude.api.entity.transporte.HistoricoTransporte;
import saude.api.entity.transporte.Agendado;
import saude.api.entity.transporte.LocalEspera;
import saude.api.entity.transporte.Motorista;
import saude.api.entity.transporte.Veiculo;
import saude.api.entity.transporte.AgendaPost.AgendarPost;
import saude.api.filtros.AgendaSearchDataNomeDTO;
import saude.api.repository.CidadaoRepository;
import saude.api.repository.UsuarioRepository;
import saude.api.repository.transporte.AgendaRepository;
import saude.api.repository.transporte.HistoricoTransporteRepository;
import saude.api.repository.transporte.IndexAgendaPacienteRepository;
import saude.api.repository.transporte.LocalEsperaRepository;
import saude.api.repository.transporte.MotoristaRepository;
import saude.api.repository.transporte.VeiculoRepository;
import saude.api.response.Response;

@Service
public class AgendaService {
	
	@Autowired
	private AgendaRepository repo;
	
	@Autowired
	private HistoricoTransporteRepository repoHist;
	
	@Autowired
	private VeiculoRepository repoVeiculo;
	
	@Autowired
	private CidadaoRepository repoCid;
	
	@Autowired
	private UsuarioRepository repoUser;
	
	@Autowired
	private MotoristaRepository repoMotorista;
	
	@Autowired
	private LocalEsperaRepository repoLocalEsp;
	
	@Autowired
	private IndexAgendaPacienteRepository repoIndex;
	
	
	
	
	
	
	//criar agenda 
	public Response<String> criarAgenda(Agenda agenda) {
		Response<String> response = new Response<>();
		try {
			//data inicial tem que ser maior que a data atual
			if(agenda.getDataInicio().isBefore(LocalDate.now())) {
				response.getErros().add("Erro: A data inicial da agenda não pode ser anterior a data atual");
				return response;
			}
			//verificar se data final não é superior a 6 meses
			if(agenda.getDataFinal().isAfter(LocalDate.now().plusMonths(6))) {
				response.getErros().add("Erro: a data final não pode ser superior a seis meses de agenda");
				return response;
			}
			//verificar se possui local de espera cadastrado
			if(agenda.getLocaisEspera().size() < 1) {
				response.getErros().add("Erro: Nenhum local de espera foi informado");
				return response;
			}
			
			
			//validar agenda
			String validar = validarCriarAgenda(agenda);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			
			//criar lugares
			//buscar veiculo
			Veiculo veiculo = repoVeiculo.findOne(agenda.getVeiculo().getId());
			if(veiculo == null) {
				response.getErros().add("Erro: não foi possível encontrar um veículo com o id "+ agenda.getVeiculo().getId());
				return response;
			}
			agenda.setVeiculo(new VeiculoDto(veiculo));
			
			//buscar Motorista
			Motorista motorista = repoMotorista.findOne(agenda.getMotorista().getId());
			if(motorista == null) {
				response.getErros().add("Erro: não foi possível encontrar um motorista com o id "+ agenda.getMotorista().getId());
				return response;
			}
			agenda.setMotorista(new MotoristaDto(motorista));
			
			List<LocalEspera> locaisEspera = new ArrayList<>();
			//buscar os locais de espera por id e montar a array
			for(LocalEspera le : agenda.getLocaisEspera()) {
				LocalEspera loc= repoLocalEsp.findOne(le.getId());
				if(loc != null)
				locaisEspera.add(loc);
			}
			if(locaisEspera.size() == 0) {
				response.getErros().add("Erro: não foi possível encontrar nenhum local de espera para a agenda que está sendo criada");
				return response;
			}
			agenda.setLocaisEspera(locaisEspera);
			//criar uma array de agenda no intervalo da data
			List<Agenda> agendas = new ArrayList<>();
			Agenda agendaTemp = null;
			LocalDate dataTemp = agenda.getDataInicio();
			LocalDate dataFinalVerif = agenda.getDataFinal().plusDays(1);
			if(dataFinalVerif.isBefore(LocalDate.now()) || dataFinalVerif.isBefore(agenda.getDataInicio())) {
				response.getErros().add("Erro: Erro no range da data, verifique a data final");
				return response;
			}
			
			//buscar usuario que esta criando a agenda
			Optional<Usuario> userLogado = repoUser.findByUsuarioIgnoreCase(buscarUsuario());
			
			//executar enquanto a data inicio não for igual a data final mais um dia
			while(!dataTemp.isEqual(dataFinalVerif)) {
				
				//verifica os dias de semana escolhidos
				if(agenda.getDiasDaSemana().contains(dataTemp.getDayOfWeek().toString())) {
					agendaTemp = new Agenda(agenda);
					//adicionar nome completo do usuári que está criando a agenda
					agendaTemp.setUsuarioCriouAgenda(userLogado.get().getNome());
					agendaTemp.setData(dataTemp);
					agendas.add(agendaTemp);
				}
				dataTemp = dataTemp.plusDays(1);
			}
			repo.save(agendas);
			//se chegou até aqui é porque as agendas foram criadas sem erro
			response.setDados("Agendas criadas com sucesso!!!");
			//criar historico
			HistoricoTransporte ht = new HistoricoTransporte();
			String acao = "Criado agenda de transporte entre "+ agenda.getDataInicio().getDayOfMonth() + 
					"/"+agenda.getDataInicio().getMonthValue()+"/"+agenda.getDataInicio().getYear()+
					" e "+agenda.getDataFinal().getDayOfMonth()+
					"/"+agenda.getDataFinal().getMonthValue()+"/"+
					agenda.getDataFinal().getYear()+"; Com veículo "+ agenda.getVeiculo()+
					" e Motorista "+agenda.getMotorista()+"; Locais de espera: ";
			for (LocalEspera local : agenda.getLocaisEspera()) {
				acao += local.getLocal() + ", horário: "+local.getHora()+", ";
				
			}
			
			ht.setAcao(acao);
			ht.setUsuario(buscarUsuario());
			ht.setDataHora(LocalDateTime.now());
			repoHist.save(ht);
			return response;	
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
		
	}//fecha criação de agenda


	
	
	
	

	private String validarCriarAgenda(Agenda agenda) {
		
		if(agenda.getNomeAgenda() == null || agenda.getNomeAgenda().length() < 2) {
			return "Erro: O nome da agenda não foi informado ou não possui caracteres";
		}
		
		if(agenda.getLocaisEspera().size() == 0) {
			return "Erro: nenhum local de espera informado";
		}
		if(agenda.getMotorista() == null) {
			return "Erro: nenhum motorista informado";
		}
		if(agenda.getVeiculo() == null) {
			return "Erro: nenhum veículo informado";
		}
		try {
			//validar se o veiculo já possui agenda no intervalo de data informado
			Long countAgenda = repo.countByVeiculoAndDataBetween(new Veiculo(agenda.getVeiculo()), agenda.getDataInicio(), agenda.getDataFinal());
			if(countAgenda > 0) {
				return "O veículo já possui alguma agenda entre os dias informados";
			}
			//validar se o motorista ja possui agenda no intervalo de data informado
			Long countMotorista = repo.countByMotoristaAndDataBetween(new Motorista(agenda.getMotorista()), agenda.getDataInicio(), agenda.getDataFinal());
			if(countMotorista > 0) {
				return "O motorista já possui alguma agenda entre os dias informados";
			}
			
			//se chegou aqui é porque foi validado com sucesso e retorna ok
			return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}//fecha validar agenda
	
	
	
	
	//remover uma agenda
	public Response<String> removerAgenda(String idagenda) {
		Response<String> response = new Response<>();
		if(idagenda == null || idagenda.equals("")) {
			response.getErros().add("Erro: O Id da agenda a ser excluída não foi informado");
			return response;
		}
		try {
			
			//buscar agenda
			Agenda agenda = repo.findOne(idagenda);
			//verificar se existe alguma cidadadao agendado
			boolean existe = false;
			for(BancoVeiculo bv : agenda.getPacientes()) {
				if(bv.getVagasOcupadas() > 0) {
					existe = true;
					break;
				}
			}
			//se exitir cidadao agendado retorna um aviso e não excluí
			if(existe) {
				response.getErros().add("A agenda não pode ser excluída, "
						+ "porque exite cidadãos agendado nela, "
						+ "remova os cidadão e tente novamente");
				return response;
			}
			//se não exitir excluí e retorn mensagem de ok
			//criar historico
			HistoricoTransporte ht = new HistoricoTransporte();
			String acao = "Excluída a agenda do dia: "+ agenda.getData();
			ht.setAcao(acao);
			ht.setDataHora(LocalDateTime.now());
			ht.setUsuario(buscarUsuario());
			repo.delete(agenda);
			response.setDados("Agenda deletada com sucesso!!!");
			repoHist.save(ht);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}//fim metodo excluir agenda
	
	
	
	
	
	
	
	
	
	
	
	
	
	//agendarPaciente em uma agenda e em um lugar no veículo
	public Response<Agenda> agendarCidadao(AgendarPost ag) {
		Response<Agenda> response = new Response<>();
		try {
			if(ag == null) {
				response.getErros().add("Erro: O objeto agendarPost é null");
				return response;
			}
			String validar = validarAgendarPost(ag);
			if(!validar.equals("ok")) {
				response.getErros().add(validar);
				return response;
			}
			//buscar agenda
			Agenda agenda = repo.findOne(ag.getIdagenda());
			     
			
			 //verificar se cidadão já está na ageda
			 Long verificar = repo.countCidadaoAgenda(agenda.getId(), ag.getNomePaciente());
			 if(verificar > 0) {
				 response.getErros().add("Erro: o cidadão informado já está agendado nesta agenda, verifique");
				 return response;
			 }
			 //buscar usuário logado 
			 String nomeUser = buscarUsuario();
			 Optional<Usuario> user = repoUser.findByUsuarioIgnoreCase(nomeUser);
			 
			 //gerarCodigo agendamento
			 int codigoAgendamento = 0;
			 Agendado indexA = repoIndex.findTop1ByData(agenda.getData());
			 if(indexA == null) {
				 codigoAgendamento = 1;
			 } else {
				 codigoAgendamento = indexA.getCodigoAgendamento() + 1;
			 }
			 Agendado idxA = new Agendado();
			 idxA.setCodigoAgendamento(codigoAgendamento);
			 idxA.setData(agenda.getData());
			 idxA.setIdPaciente(ag.getIdcidadao());
			 idxA.setNomePaciente(ag.getNomePaciente());
			 idxA.setLocalDestino(agenda.getNomeAgenda());
			 idxA.setIdAgenda(agenda.getId());
			 repoIndex.save(idxA);
			 
			 
			 //adicionar informações local espera do paciente e usuário que agendou
			String agendarRetorno = agenda.agendarCidadao(ag, codigoAgendamento, user.get());
			
			if(!agendarRetorno.equals("ok")) {
				response.getErros().add(agendarRetorno);
				return response;
			}
			
			//se chegou aqui é porque tudo ocorreu bem então é só gravar agenda
			Agenda agendaSalva = repo.save(agenda);
			response.setDados(agendaSalva);
		    return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}







	private String validarAgendarPost(AgendarPost ag) {
		try {
			if(ag.getIdcidadao() == null || ag.getIdcidadao().length() < 1) {
				return "Erro: O id do cidadão não foi informado";
			}
			//verificar se existe um cidadão com o id informado
			 Long countCid = repoCid.countById(ag.getIdcidadao());
			 if(countCid == 0) {
				 return "Erro: Não foi encontrado nenhum cidadão no banco de dados com o id --> "+ag.getIdcidadao();
			 }
			
		    if(ag.getLocalEspera() == null || ag.getLocalEspera().length() < 2) {
				return "Erro: O local de espera não foi informado";
			}
			
			if(ag.getIdagenda() == null || ag.getIdagenda().length() < 1) {
				return "Erro: O id da agenda não foi informado";
			}
			//verificar se agenda exite
			 Long countAg = repo.countById(ag.getIdagenda());
			 if(countAg == 0) {
				 return "Não foi possível encontrar uma agenda com o id fornecido";
			 }
			 if(ag.getNomePaciente() == null && ag.getNomePaciente().length() < 2 ) {
				 return "Erro: Não foi fornecido o nome do paciente corretamente";
			 }
			 return "ok";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	
	
	
	
	
	//buscar todas agendas de determinada data
		public Response<Page<AgendaDto>> buscarAgendaNomes(String nome,String data, int page, int size) {
			Response<Page<AgendaDto>> response = new Response<>();
			LocalDate dataAgenda;
			
			if(data == null || data.length() < 10) {
				response.getErros().add("Erro: Erro no formato da data verifique se o formato está como yyyy-MM-dd");
				return response;
			}
			try {
				//converter formato string para date
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			     dataAgenda = LocalDate.parse(data, df);
			} catch (Exception e) {
				response.getErros().add("Erro ao tentar converter a data: " + e.getMessage());
				return response;	
			}
			
			
			try {
				Pageable pageable = new PageRequest(page, size);
				List<Agenda> agendas;
				List<AgendaDto> agendasDto = new ArrayList<>(); 
				if(nome == null || nome.equals("") || nome.equals("undefined")) {
					agendas = repo.findByData(dataAgenda, pageable);
				} else {
					agendas = repo.findByNomeAgendaLikeIgnoreCaseAndData(nome, dataAgenda, pageable);
				}
				for(Agenda a : agendas) {
					AgendaDto adto = new AgendaDto(a);
					agendasDto.add(adto);
				}
				Page<AgendaDto> agendaDtoPage = new PageImpl<>(agendasDto, pageable, agendasDto.size());
				response.setDados(agendaDtoPage);
				return response;
			} catch (Exception e) {
				response.getErros().add(e.getMessage());
				return response;
			}
			
		}//fecha busca por todos os destinos

		
		
		
	/*
	
	public Response<List<Agenda>> buscarEmTodasAgendasDoDiaCidadao(String idcidadao, String data) {
		Response<List<Agenda>> response = new Response<>();
		if(idcidadao == null || idcidadao.length() < 2) {
			response.getErros().add("Erro ao tentar localizar a agenda do cidadão, o id do cidadão não foi passado");
			return response;
		}
		if(data == null || data.length() < 5) {
			response.getErros().add("Erro ao tentar localizar a agenda do cidadão, a data não está correta");
			return response;
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dataAgenda =  LocalDate.parse(data,formatter);
			List<Agenda> agendas = repo.buscarPorDataENome(idcidadao, dataAgenda);
			response.setDados(agendas);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}
	
	*/
	
	
	//remoção do paciente agendado
	public Response<Agenda> removerAgendaCidadao(String idagenda, String nomePaciente, String nomeAcompanhante) {
		Response<Agenda> response = new Response<>();
		 String acao = ""; //acao do historico
		//validar id agenda e id do cidadao
		if(idagenda == null || idagenda.length() < 2) {
			response.getErros().add("Erro ao tentar remover cidadão da agenda: O id da agenda não foi passado");
			return response;
		}
		if(nomePaciente == null || nomePaciente.length() < 2) {
			response.getErros().add("Erro ao tentar remover cidadão da agenda: O nome do cidadão não foi passado");
			return response;
		}
		
		
		try {
			//buscar agenda
			Agenda agenda = repo.findOne(idagenda);
			if(agenda == null) {
				response.getErros().add("Não foi possível encontrar a agenda com o valor do id passado");
				return response;
			}
			//verificar se agenda não está vazia
			 boolean existe = false ;//verifica se existe cidadãos agendado
			 for(BancoVeiculo bv : agenda.getPacientes()) {
				 if(bv.getVagasOcupadas() > 0) {
					 existe = true;
					 break;
				 }
			 }
			 if(!existe) {
				 response.getErros().add("A agenda não possui cidadãos agendados");
				 return response;
			 }
			 
			 
			
				
	
				DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				acao += "No dia "+ agenda.getData().format(f);
				
				Agendado idx = repoIndex.findByIdAgendaAndDataAndNomePaciente(agenda.getId(), agenda.getData(), nomePaciente);
				if(idx != null) {
					repoIndex.delete(idx);
				}
				//remover cidadao do local ocupado e redifinir o lugar como vago
				int count = 0;	
				for(BancoVeiculo bv : agenda.getPacientes()) {
					if(bv.getNomePaciente().equals(nomePaciente)) {
					   break;
					}
					count = count +1;
				   if(bv.getNomeAcompanhante().equals(nomeAcompanhante)) {
					   bv.setNomeAcompanhante("");
					   bv.setVagasOcupadas(bv.getVagasOcupadas() - 1);
				   }
				 }
			 agenda.getPacientes().remove(count);	
			
			HistoricoTransporte ht = new HistoricoTransporte();
			ht.setAcao(acao);
			ht.setDataHora(LocalDateTime.now());
			ht.setUsuario(buscarUsuario());
			//se chegou aqui tudo ocorreu bem é só salvar a agenda
			Agenda agSalva = repo.save(agenda);
			response.setDados(agSalva);
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







	public Response<Agenda> buscarPorId(String idagenda) {
		Response<Agenda> response = new Response<>();
		try {
			if(idagenda == null || idagenda.length() < 1) {
				response.getErros().add("Erro: não foi passado o id da agenda a ser consultada");
				return response;
			}
			Agenda ag = repo.findOne(idagenda);
			if(ag == null) {
				response.getErros().add("Não foi encontrada nenhum agenda com o valor do id "+ idagenda);
				return response;
			}
			response.setDados(ag);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}






    //busca todas agendas por nome e um range de data   
	public Response<Page<AgendaDto>> buscarPorNomeIntervaloData(AgendaSearchDataNomeDTO ag) {
		Response<Page<AgendaDto>> response = new Response<>();
		//validar 
	
		if(ag.getDataInicio() == null) {
			response.getErros().add("Erro: não foi informado a data de início para consulta da agenda");
			return response;
		}
		if(ag.getDataFim() == null) {
			response.getErros().add("Erro: não foi informado a data de final para consulta da agenda");
			return response;
		}
		if(ag.getSize() == 0) {
			ag.setSize(8);
		}
		try {
			List<Agenda> agendas = null;
			Pageable pageable = new PageRequest(ag.getPage(), ag.getSize());
			if(ag.getNomeAgenda() == null || ag.getNomeAgenda().length() < 1 || ag.getNomeAgenda().equals("undefined")) {
				agendas = repo.findByDataBetween(ag.getDataInicio(), ag.getDataFim(), pageable);
			} else {
				agendas =  repo.findByNomeAgendaLikeIgnoreCaseAndDataBetween(ag.getNomeAgenda(), ag.getDataInicio(), ag.getDataFim(), pageable);
			}
			
			List<AgendaDto> agendasDto = new ArrayList<>();
			for(Agenda a : agendas) {
			  AgendaDto agDto = new AgendaDto(a);	
			  agendasDto.add(agDto);
			}
			Page<AgendaDto> agDtoPage = new PageImpl<>(agendasDto, pageable, agendasDto.size());
			//response.setDados(agendaDto);
			response.setDados(agDtoPage);
			return response;
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}






    //busca todas as agendas que contenham o nome do paciente
	public Response<Page<AgendaDto>> buscarAgendasPaciente(String nome, int page, int size) {
		Response<Page<AgendaDto>> response = new Response<>();
		if(nome == null || nome.length() < 1) {
			response.getErros().add("Erro: O nome do paciente não foi informado corretamente");
			return response;
		}
		 
		try {
			//verificar se nome exites no banco de dados
			Long pacienteCount = repoCid.countByNomeIgnoreCase(nome);
			if(pacienteCount == 0) {
				response.getErros().add("Erro: Não foi possível encontrar um pacinete com o nome digitado");
				return response;
			}
			Pageable pageable = new PageRequest(page, size);
			List<Agenda> agendas = repo.buscarTodasNomePaciente(nome, pageable);
			List<AgendaDto> agendasDto = new ArrayList<>();
			for(Agenda a : agendas) {
			  AgendaDto agDto = new AgendaDto(a);	
			  agendasDto.add(agDto);
			}
			Page<AgendaDto> agDtoPage = new PageImpl<>(agendasDto, pageable, agendasDto.size());
			response.setDados(agDtoPage);
			return response;
			
		} catch (Exception e) {
			response.getErros().add(e.getMessage());
			return response;
		}
	}








	


	

}//fecha classe
