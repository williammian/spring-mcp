package br.com.wm.springmcp.repository;

import br.com.wm.springmcp.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    
    Optional<Venda> findByNumero(Integer numero);
    
    boolean existsByNumero(Integer numero);
    
    boolean existsByNumeroAndIdNot(Integer numero, Long id);
    
    List<Venda> findByClienteId(Long clienteId);
    
    List<Venda> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);
    
    @Query("SELECT v FROM Venda v WHERE v.data >= :dataInicio AND v.data <= :dataFim ORDER BY v.data DESC")
    List<Venda> findVendasPorPeriodo(@Param("dataInicio") LocalDate dataInicio, 
                                     @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT v FROM Venda v JOIN FETCH v.cliente WHERE v.id = :id")
    Optional<Venda> findByIdWithCliente(@Param("id") Long id);
    
    @Query("SELECT v FROM Venda v JOIN FETCH v.cliente JOIN FETCH v.itens i JOIN FETCH i.produto WHERE v.id = :id")
    Optional<Venda> findByIdWithClienteAndItens(@Param("id") Long id);
}