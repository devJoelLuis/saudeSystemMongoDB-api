package saude.api.repository.transporte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.transporte.Veiculo;

public interface VeiculoRepository extends MongoRepository<Veiculo, String> {

	Page<Veiculo> findByVeiculoIgnoreCase(String nome, Pageable pageable);

	Long countById(String id);

	Long countByPlacaIgnoreCase(String placa);

	Long countByPlacaIgnoreCaseAndIdNot(String placa, String id);

	Page<Veiculo> findByVeiculoLikeIgnoreCase(String param, Pageable pageable);

	Page<Veiculo> findByPlacaLikeIgnoreCase(String param, Pageable pageable);

}
