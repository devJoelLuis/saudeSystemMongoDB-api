package saude.api.repository.transporte;

import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.transporte.HistoricoTransporte;

public interface HistoricoTransporteRepository extends MongoRepository<HistoricoTransporte, String> {

}
