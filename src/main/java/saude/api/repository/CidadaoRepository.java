package saude.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import saude.api.entity.Cidadao;

public interface CidadaoRepository extends MongoRepository<Cidadao, String> {
	
	public Page<Cidadao> findAll(Pageable pageable);
	public Cidadao findByCartaosus(String cartaosus);
	public Page<Cidadao> findByNomeContainsIgnoreCaseOrderByNomeAsc(Pageable pageable, String nome);
	public Long countById(String id);
	public Long countByNomeIgnoreCase(String nome);
	public Long countByCartaocid(String cartaocid);
	public Long countByRgNumero(String rgNumero);
	public Cidadao findByRgNumero(String rgNumero);
	public Long countByCartaosus(String cartaosus);
	public Cidadao findByNumeroProntuario(Integer numProntuario);
	public Long countByCpf(String cpf);
	public Cidadao findByCpf(String cpf);
	public Long countByNumeroProntuario(int novoNumProntuario);
	public Cidadao findFirstByOrderByNumeroProntuarioDesc();
	public Long countByNomeIgnoreCaseAndIdNot(String nome, String id);
	public Long countByCartaocidAndIdNot(String cartaocid, String id);
	public Long countByRgNumeroAndIdNot(String rgNumero, String id);
	public Long countByCartaosusAndIdNot(String cartaosus, String id);
	public Long countByCpfAndIdNot(String cpf, String id);
	public Cidadao findByNomeIgnoreCase(String nome);
	public List<Cidadao> findByNomeLikeIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);
	public Cidadao findByCartaocid(String ccid);
	
	

}
