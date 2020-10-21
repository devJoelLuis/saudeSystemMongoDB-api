package saude.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.HistoricoUbs;

public interface HistoricoUbsRepository extends MongoRepository<HistoricoUbs, String> {

}
