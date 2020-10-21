package saude.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.HistoricoUsuario;

public interface HistoricoUsuarioRepository extends MongoRepository<HistoricoUsuario, String> {

}
