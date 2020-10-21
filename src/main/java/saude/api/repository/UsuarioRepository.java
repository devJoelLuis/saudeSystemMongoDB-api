package saude.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	Long countById(String iduser);

	Optional<Usuario> findByUsuarioIgnoreCase(String usuario);

	Long countByUsuarioIgnoreCase(String usuario);

	Long countByNomeIgnoreCaseAndIdNot(String nome, String id);

	Long countByIgnoreCaseUsuarioAndIdNot(String usuario, String id);

	Long countByNomeIgnoreCase(String nome);

	Page<Usuario> findByNomeLikeIgnoreCase(String nome, Pageable pageable);

	

}
