package br.com.wm.springmcp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "vendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "itens")
public class Venda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;
    
    @Column(name = "numero", unique = true, nullable = false)
    @NotNull(message = "Número da venda é obrigatório")
    private Integer numero;
    
    @Column(name = "data", nullable = false)
    @NotNull(message = "Data da venda é obrigatória")
    private LocalDate data;
    
    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "Total da venda é obrigatório")
    private BigDecimal total;
    
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItensVenda> itens;
    
    public Venda(Cliente cliente, Integer numero, LocalDate data, BigDecimal total) {
        this.cliente = cliente;
        this.numero = numero;
        this.data = data;
        this.total = total;
    }
}