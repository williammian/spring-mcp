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
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", length = 10, unique = true, nullable = false)
    @NotBlank(message = "Código não pode estar em branco")
    @Size(max = 10, message = "Código deve ter no máximo 10 caracteres")
    private String codigo;
    
    @Column(name = "nome", length = 200, nullable = false)
    @NotBlank(message = "Nome não pode estar em branco")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    private String nome;
    
    @Column(name = "documento", length = 20, nullable = false)
    @NotBlank(message = "Documento não pode estar em branco")
    @Size(max = 20, message = "Documento deve ter no máximo 20 caracteres")
    private String documento;
    
    public Cliente(String codigo, String nome, String documento) {
        this.codigo = codigo;
        this.nome = nome;
        this.documento = documento;
    }
}