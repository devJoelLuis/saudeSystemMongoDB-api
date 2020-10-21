package saude.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.Ubs;

public interface UbsRespository extends MongoRepository<Ubs, String> {

	Page<Ubs> findAll(Pageable pageable);

	Long countById(String id);

	Long countByNomeIgnoreCaseAndIdNot(String nome, String id);

	Long countByNomeIgnoreCase(String nome);
}
