package br.com.wm.springmcp.repository;

import br.com.wm.springmcp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    Optional<Produto> findByCodigo(String codigo);
    
    boolean existsByCodigo(String codigo);
    
    boolean existsByDescricao(String descricao);
    
    boolean existsByCodigoAndIdNot(String codigo, Long id);
    
    boolean existsByDescricaoAndIdNot(String descricao, Long id);
}