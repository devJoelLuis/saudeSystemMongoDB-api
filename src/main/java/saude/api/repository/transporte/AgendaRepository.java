package saude.api.repository.transporte;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import saude.api.entity.transporte.Agenda;
import saude.api.entity.transporte.Motorista;
import saude.api.entity.transporte.Veiculo;

public interface AgendaRepository extends MongoRepository<Agenda, String> {

	Long countByVeiculoAndDataBetween(Veiculo veiculo, LocalDate dataInicio, LocalDate dataFinal);

	Long countByMotoristaAndDataBetween(Motorista motorista, LocalDate dataInicio, LocalDate dataFinal);


	Long countById(String idagenda);

	// verificar se um cidadadão já esta na agenda
	@Query(value = "{'$and' : [{'id' : ?0}," + "{'pacientes.nomePaciente' : ?1 } ]}", count = true)
	Long countCidadaoAgenda(String id, String nomePaciente);

	// busca a agenda do cidadao pelo id e pela data usado para verificar se cidadão
	// já possui agenda no dia
	// e alertar o usuário
	/*
	@Query("{$and : [{ 'pacientes.cidadao.id' : ?0 }, { 'data': ?1 } ]}")
	List<Agenda> buscarPorDataENome(String idcidadao, LocalDate dataAgenda);

	
	 * @Query("{$and : [{ 'locaisDestino.local' : ?0 }, { 'data': {'$gte' : ?1}}, {'data': {'$lte': ?2 }} ]}"
	 * ) List<Agenda> procurarPorLocalDestinoDataIniDataFinal(String destino,
	 * LocalDate dataInicio, LocalDate dataFinal, Pageable pageable);
	 * 
	 * 
	 * @Query("{ '$and': [{'locaisDestino.local' : { '$regex' : ?0 , $options: 'i'}}, {'data' : { '$gte' : ?1 }}]}"
	 * ) Page<Agenda> buscarPorDestinoSemHora(String dest,LocalDate data, Pageable
	 * pageable);
	 * 
	 * @Query("{'$and': [ {'locaisDestino.local' :  { '$regex' : ?0 , $options: 'i'}},"
	 * + "{'locaisDestino.hora' : { '$regex' : ?2 , $options: 'i'}}," +
	 * "{'data':{'$gte' : ?1 }} ]}") Page<Agenda> buscarPorDestinoComHora(String
	 * dest,LocalDate data, String hora, Pageable pageable);
	 * 
	 * @Query("{'$and': [ {'locaisDestino.local' :  { '$regex' : ?0 , $options: 'i'}},"
	 * + "{'data' : ?1} ]}") List<Agenda> procurarPorLocalDestino(String local,
	 * LocalDate data, Pageable pageable);
	 * 
	 * 
	 */



	List<Agenda> findByNomeAgendaLikeIgnoreCaseAndDataBetween(String nomeAgenda, LocalDate dataInicio,
			LocalDate dataFim, Pageable pageable);

	List<Agenda> findByNomeAgendaLikeIgnoreCaseAndData(String nome, LocalDate dataAgenda, Pageable pageable);

	List<Agenda> findByData(LocalDate dataAgenda, Pageable pageable);

	@Query(value ="{'pacientes.nomePaciente' : ?1 }")
	List<Agenda> buscarTodasNomePaciente(String nome, Pageable pageable);

	List<Agenda> findByDataBetween(LocalDate dataInicio, LocalDate dataFim, Pageable pageable);


}
