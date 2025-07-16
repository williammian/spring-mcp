package br.com.wm.springmcp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wm.springmcp.model.Produto;
import br.com.wm.springmcp.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    
    public List<Produto> findAll() {
        log.info("Buscando todos os produtos");
        return produtoRepository.findAll();
    }
    
    public Optional<Produto> findById(Long id) {
        log.info("Buscando produto por ID: {}", id);
        return produtoRepository.findById(id);
    }
    
    public Optional<Produto> findByCodigo(String codigo) {
        log.info("Buscando produto por código: {}", codigo);
        return produtoRepository.findByCodigo(codigo);
    }
    
    public Produto save(Produto produto) {
        log.info("Salvando produto: {}", produto.getCodigo());
        validateProduto(produto);
        return produtoRepository.save(produto);
    }
    
    public Produto update(Long id, Produto produto) {
        log.info("Atualizando produto ID: {}", id);
        
        Produto existingProduto = produtoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        
        validateProdutoForUpdate(produto, id);
        
        existingProduto.setCodigo(produto.getCodigo());
        existingProduto.setDescricao(produto.getDescricao());
        
        return produtoRepository.save(existingProduto);
    }
    
    public void deleteById(Long id) {
        log.info("Deletando produto ID: {}", id);
        
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        
        produtoRepository.deleteById(id);
    }
    
    private void validateProduto(Produto produto) {
        if (produtoRepository.existsByCodigo(produto.getCodigo())) {
            throw new RuntimeException("Já existe um produto com o código: " + produto.getCodigo());
        }
        
        if (produtoRepository.existsByDescricao(produto.getDescricao())) {
            throw new RuntimeException("Já existe um produto com a descrição: " + produto.getDescricao());
        }
    }
    
    private void validateProdutoForUpdate(Produto produto, Long id) {
        if (produtoRepository.existsByCodigoAndIdNot(produto.getCodigo(), id)) {
            throw new RuntimeException("Já existe um produto com o código: " + produto.getCodigo());
        }
        
        if (produtoRepository.existsByDescricaoAndIdNot(produto.getDescricao(), id)) {
            throw new RuntimeException("Já existe um produto com a descrição: " + produto.getDescricao());
        }
    }
}