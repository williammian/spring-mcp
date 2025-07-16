package br.com.wm.springmcp.repository;

import br.com.wm.springmcp.model.ItensVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensVendaRepository extends JpaRepository<ItensVenda, Long> {
    
    List<ItensVenda> findByVendaId(Long vendaId);
    
    List<ItensVenda> findByVendaIdOrderBySeq(Long vendaId);
    
    List<ItensVenda> findByProdutoId(Long produtoId);
    
    boolean existsByVendaIdAndSeq(Long vendaId, Integer seq);
    
    @Query("SELECT i FROM ItensVenda i JOIN FETCH i.produto WHERE i.venda.id = :vendaId ORDER BY i.seq")
    List<ItensVenda> findByVendaIdWithProduto(@Param("vendaId") Long vendaId);
    
    @Query("SELECT i FROM ItensVenda i WHERE i.venda.id = :vendaId AND i.seq = :seq")
    ItensVenda findByVendaIdAndSeq(@Param("vendaId") Long vendaId, @Param("seq") Integer seq);
}