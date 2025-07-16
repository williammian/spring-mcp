package br.com.wm.springmcp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    @NotBlank(message = "Código não pode estar em branco")
    @Size(max = 10, message = "Código deve ter no máximo 10 caracteres")
    private String codigo;
    
    @Column(name = "descricao", length = 200, unique = true, nullable = false)
    @NotBlank(message = "Descrição não pode estar em branco")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String descricao;
    
    public Produto(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}