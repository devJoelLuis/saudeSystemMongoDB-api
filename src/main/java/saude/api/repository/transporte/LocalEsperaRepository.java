package saude.api.repository.transporte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.transporte.LocalEspera;

public interface LocalEsperaRepository extends MongoRepository<LocalEspera, String>{

	Page<LocalEspera> findByLocalIgnoreCase(String nome, Pageable pageable);

	Long countById(String id);

	Page<LocalEspera> findByLocalLikeIgnoreCase(String nome, Pageable pageable);

}
