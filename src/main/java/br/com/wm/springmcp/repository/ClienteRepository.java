package br.com.wm.springmcp.repository;

import br.com.wm.springmcp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByCodigo(String codigo);
    
    boolean existsByCodigo(String codigo);
    
    boolean existsByCodigoAndIdNot(String codigo, Long id);
    
    boolean existsByDocumento(String documento);
    
    boolean existsByDocumentoAndIdNot(String documento, Long id);
}