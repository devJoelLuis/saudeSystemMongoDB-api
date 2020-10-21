package saude.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.HistoricoCidadao;

public interface HistoricoCidadaoRepository extends MongoRepository<HistoricoCidadao, String> {

}
