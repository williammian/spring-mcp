package br.com.wm.springmcp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wm.springmcp.model.Venda;
import br.com.wm.springmcp.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VendaController {
    
    private final VendaService vendaService;
    
    @GetMapping
    public ResponseEntity<List<Venda>> findAll() {
        List<Venda> vendas = vendaService.findAll();
        return ResponseEntity.ok(vendas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Venda> findById(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.findById(id);
        return venda.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/numero/{numero}")
    public ResponseEntity<Venda> findByNumero(@PathVariable Integer numero) {
        Optional<Venda> venda = vendaService.findByNumero(numero);
        return venda.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venda>> findByClienteId(@PathVariable Long clienteId) {
        List<Venda> vendas = vendaService.findByClienteId(clienteId);
        return ResponseEntity.ok(vendas);
    }
    
    @GetMapping("/periodo")
    public ResponseEntity<List<Venda>> findByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<Venda> vendas = vendaService.findByPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(vendas);
    }
    
    @PostMapping
    public ResponseEntity<Venda> create(@Valid @RequestBody Venda venda) {
        try {
            Venda savedVenda = vendaService.save(venda);
            return ResponseEntity.ok(savedVenda);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Venda> update(@PathVariable Long id, @Valid @RequestBody Venda venda) {
        try {
            Venda updatedVenda = vendaService.update(id, venda);
            return ResponseEntity.ok(updatedVenda);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            vendaService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}