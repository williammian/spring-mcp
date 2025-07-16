package br.com.wm.springmcp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wm.springmcp.model.Produto;
import br.com.wm.springmcp.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProdutoController {
    
    private final ProdutoService produtoService;
    
    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.findById(id);
        return produto.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Produto> findByCodigo(@PathVariable String codigo) {
        Optional<Produto> produto = produtoService.findByCodigo(codigo);
        return produto.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Produto> create(@Valid @RequestBody Produto produto) {
        try {
            Produto savedProduto = produtoService.save(produto);
            return ResponseEntity.ok(savedProduto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        try {
            Produto updatedProduto = produtoService.update(id, produto);
            return ResponseEntity.ok(updatedProduto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            produtoService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}