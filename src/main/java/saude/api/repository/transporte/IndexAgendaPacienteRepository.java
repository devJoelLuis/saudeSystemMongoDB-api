package saude.api.repository.transporte;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.transporte.Agendado;

public interface IndexAgendaPacienteRepository extends MongoRepository<Agendado, String> {

	Agendado findTop1ByData(LocalDate data);

	Agendado findByIdAgendaAndDataAndNomePaciente(String id, LocalDate data, String nomePaciente);



}
