package br.com.wm.springmcp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wm.springmcp.model.Cliente;
import br.com.wm.springmcp.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public List<Cliente> findAll() {
        log.info("Buscando todos os clientes");
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> findById(Long id) {
        log.info("Buscando cliente por ID: {}", id);
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> findByCodigo(String codigo) {
        log.info("Buscando cliente por código: {}", codigo);
        return clienteRepository.findByCodigo(codigo);
    }
    
    public Cliente save(Cliente cliente) {
        log.info("Salvando cliente: {}", cliente.getCodigo());
        validateCliente(cliente);
        return clienteRepository.save(cliente);
    }
    
    public Cliente update(Long id, Cliente cliente) {
        log.info("Atualizando cliente ID: {}", id);
        
        Cliente existingCliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        
        validateClienteForUpdate(cliente, id);
        
        existingCliente.setCodigo(cliente.getCodigo());
        existingCliente.setNome(cliente.getNome());
        existingCliente.setDocumento(cliente.getDocumento());
        
        return clienteRepository.save(existingCliente);
    }
    
    public void deleteById(Long id) {
        log.info("Deletando cliente ID: {}", id);
        
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        
        clienteRepository.deleteById(id);
    }
    
    private void validateCliente(Cliente cliente) {
        if (clienteRepository.existsByCodigo(cliente.getCodigo())) {
            throw new RuntimeException("Já existe um cliente com o código: " + cliente.getCodigo());
        }
        
        if (clienteRepository.existsByDocumento(cliente.getDocumento())) {
            throw new RuntimeException("Já existe um cliente com o documento: " + cliente.getDocumento());
        }
    }
    
    private void validateClienteForUpdate(Cliente cliente, Long id) {
        if (clienteRepository.existsByCodigoAndIdNot(cliente.getCodigo(), id)) {
            throw new RuntimeException("Já existe um cliente com o código: " + cliente.getCodigo());
        }
        
        if (clienteRepository.existsByDocumentoAndIdNot(cliente.getDocumento(), id)) {
            throw new RuntimeException("Já existe um cliente com o documento: " + cliente.getDocumento());
        }
    }
}