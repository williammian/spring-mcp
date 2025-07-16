package br.com.wm.springmcp.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "itens_venda", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_venda", "seq"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "venda")
public class ItensVenda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venda", nullable = false)
    @NotNull(message = "Venda é obrigatória")
    private Venda venda;
    
    @Column(name = "seq", nullable = false)
    @NotNull(message = "Sequência é obrigatória")
    private Integer seq;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", nullable = false)
    @NotNull(message = "Produto é obrigatório")
    private Produto produto;
    
    @Column(name = "qtde", precision = 10, scale = 3, nullable = false)
    @NotNull(message = "Quantidade é obrigatória")
    private BigDecimal qtde;
    
    @Column(name = "unit", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "Valor unitário é obrigatório")
    private BigDecimal unit;
    
    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    @NotNull(message = "Total do item é obrigatório")
    private BigDecimal total;
    
    public ItensVenda(Venda venda, Integer seq, Produto produto, BigDecimal qtde, BigDecimal unit, BigDecimal total) {
        this.venda = venda;
        this.seq = seq;
        this.produto = produto;
        this.qtde = qtde;
        this.unit = unit;
        this.total = total;
    }
}