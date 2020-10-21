package saude.api.repository.transporte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.transporte.Motorista;

public interface MotoristaRepository extends MongoRepository<Motorista, String> {

	Page<Motorista> findByNomeMotoristaIgnoreCase(String nome, Pageable pageable);

	Long countById(String id);

	Long countByNomeMotoristaIgnoreCase(String nomeMotorista);

	Long countByNomeMotoristaIgnoreCaseAndIdNot(String nomeMotorista, String id);

	Page<Motorista> findByNomeMotoristaLikeIgnoreCaseOrderByNomeMotorista(String nome, Pageable pageable);

	Page<Motorista> findByOrderByNomeMotoristaAsc(Pageable pageable);



}
