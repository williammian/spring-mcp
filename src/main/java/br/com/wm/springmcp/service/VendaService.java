package br.com.wm.springmcp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wm.springmcp.model.ItensVenda;
import br.com.wm.springmcp.model.Venda;
import br.com.wm.springmcp.repository.ItensVendaRepository;
import br.com.wm.springmcp.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VendaService {
    
    private final VendaRepository vendaRepository;
    private final ItensVendaRepository itensVendaRepository;
    
    public List<Venda> findAll() {
        log.info("Buscando todas as vendas");
        return vendaRepository.findAll();
}
    
    public Optional<Venda> findById(Long id) {
        log.info("Buscando venda por ID: {}", id);
        return vendaRepository.findByIdWithClienteAndItens(id);
    }
    
    public Optional<Venda> findByNumero(Integer numero) {
        log.info("Buscando venda por número: {}", numero);
        return vendaRepository.findByNumero(numero);
    }
    
    public List<Venda> findByClienteId(Long clienteId) {
        log.info("Buscando vendas do cliente ID: {}", clienteId);
        return vendaRepository.findByClienteId(clienteId);
    }
    
    public List<Venda> findByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        log.info("Buscando vendas do período: {} a {}", dataInicio, dataFim);
        return vendaRepository.findVendasPorPeriodo(dataInicio, dataFim);
    }
    
    public Venda save(Venda venda) {
        log.info("Salvando venda número: {}", venda.getNumero());
        validateVenda(venda);
        
        // Calcula o total da venda baseado nos itens
        if (venda.getItens() != null && !venda.getItens().isEmpty()) {
            BigDecimal total = venda.getItens().stream()
                .map(ItensVenda::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            venda.setTotal(total);
        }
        
        Venda savedVenda = vendaRepository.save(venda);
        
        // Salva os itens da venda
        if (venda.getItens() != null) {
            for (ItensVenda item : venda.getItens()) {
                item.setVenda(savedVenda);
                // Calcula o total do item
                item.setTotal(item.getQtde().multiply(item.getUnit()));
            }
            itensVendaRepository.saveAll(venda.getItens());
        }
        
        return savedVenda;
    }
    
    public Venda update(Long id, Venda venda) {
        log.info("Atualizando venda ID: {}", id);
        
        Venda existingVenda = vendaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Venda não encontrada com ID: " + id));
        
        validateVendaForUpdate(venda, id);
        
        existingVenda.setCliente(venda.getCliente());
        existingVenda.setNumero(venda.getNumero());
        existingVenda.setData(venda.getData());
        
        // Remove itens antigos
        itensVendaRepository.deleteAll(itensVendaRepository.findByVendaId(id));
        
        // Calcula o total da venda baseado nos itens
        if (venda.getItens() != null && !venda.getItens().isEmpty()) {
            BigDecimal total = venda.getItens().stream()
                .map(item -> item.getQtde().multiply(item.getUnit()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            existingVenda.setTotal(total);
        }
        
        Venda savedVenda = vendaRepository.save(existingVenda);
        
        // Salva os novos itens
        if (venda.getItens() != null) {
            for (ItensVenda item : venda.getItens()) {
                item.setVenda(savedVenda);
                item.setTotal(item.getQtde().multiply(item.getUnit()));
            }
            itensVendaRepository.saveAll(venda.getItens());
        }
        
        return savedVenda;
    }
    
    public void deleteById(Long id) {
        log.info("Deletando venda ID: {}", id);
        
        if (!vendaRepository.existsById(id)) {
            throw new RuntimeException("Venda não encontrada com ID: " + id);
        }
        
        // Remove itens da venda primeiro
        itensVendaRepository.deleteAll(itensVendaRepository.findByVendaId(id));
        
        vendaRepository.deleteById(id);
    }
    
    private void validateVenda(Venda venda) {
        if (vendaRepository.existsByNumero(venda.getNumero())) {
            throw new RuntimeException("Já existe uma venda com o número: " + venda.getNumero());
        }
    }
    
    private void validateVendaForUpdate(Venda venda, Long id) {
        if (vendaRepository.existsByNumeroAndIdNot(venda.getNumero(), id)) {
            throw new RuntimeException("Já existe uma venda com o número: " + venda.getNumero());
        }
    }
}