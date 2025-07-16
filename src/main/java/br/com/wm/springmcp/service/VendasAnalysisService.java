package br.com.wm.springmcp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendasAnalysisService {
    
    private final JdbcTemplate jdbcTemplate;
    
    public Map<String, Object> getTotalVendasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        String sql = """
            SELECT 
                COUNT(*) as total_vendas,
                COALESCE(SUM(total), 0) as valor_total,
                COALESCE(AVG(total), 0) as valor_medio,
                (?::date - ?::date) + 1 as dias_periodo
            FROM vendas 
            WHERE data BETWEEN ? AND ?
            """;
        
        return jdbcTemplate.queryForMap(sql, dataFim, dataInicio, dataInicio, dataFim);
    }
    
    public List<Map<String, Object>> getVendasPorMes(int ano) {
        String sql = """
            SELECT 
                EXTRACT(MONTH FROM data) as mes,
                COUNT(*) as total_vendas,
                COALESCE(SUM(total), 0) as valor_total
            FROM vendas 
            WHERE EXTRACT(YEAR FROM data) = ?
            GROUP BY EXTRACT(MONTH FROM data)
            ORDER BY mes
            """;
        
        return jdbcTemplate.queryForList(sql, ano);
    }
    
    public List<Map<String, Object>> getTopClientes(int limite) {
        String sql = """
            SELECT 
                c.nome as cliente_nome,
                c.codigo as cliente_codigo,
                COUNT(v.id) as total_vendas,
                COALESCE(SUM(v.total), 0) as valor_total,
                COALESCE(AVG(total), 0) as valor_medio
            FROM clientes c
            INNER JOIN vendas v ON c.id = v.id_cliente
            GROUP BY c.nome, c.codigo
            ORDER BY valor_total DESC
            LIMIT ?
            """;
        
        return jdbcTemplate.queryForList(sql, limite);
    }
    
    public List<Map<String, Object>> getTopProdutos(int limite) {
        String sql = """
            SELECT 
                p.descricao as produto_descricao,
                p.codigo as produto_codigo,
                COUNT(iv.id) as total_vendas,
                COALESCE(SUM(iv.qtde), 0) as quantidade_total,
                COALESCE(SUM(iv.total), 0) as valor_total,
                COALESCE(AVG(iv.total), 0) as valor_medio
            FROM produtos p
            INNER JOIN itens_venda iv ON p.id = iv.id_produto
            GROUP BY p.descricao, p.codigo
            ORDER BY valor_total DESC
            LIMIT ?
            """;
        
        return jdbcTemplate.queryForList(sql, limite);
    }
    
    public List<Map<String, Object>> getVendasPorDia(LocalDate dataInicio, LocalDate dataFim) {
        String sql = """
            SELECT 
                data,
                COUNT(*) as total_vendas,
                COALESCE(SUM(total), 0) as valor_total
            FROM vendas 
            WHERE data BETWEEN ? AND ?
            GROUP BY data
            ORDER BY data
            """;
        
        return jdbcTemplate.queryForList(sql, dataInicio, dataFim);
    }
    
    public Map<String, Object> getResumoGeral() {
        String sql = """
            SELECT 
                (SELECT COUNT(*) FROM clientes) as total_clientes,
                (SELECT COUNT(*) FROM produtos) as total_produtos,
                (SELECT COUNT(*) FROM vendas) as total_vendas,
                (SELECT COALESCE(SUM(total), 0) FROM vendas) as valor_total_vendas,
                (SELECT COALESCE(AVG(total), 0) FROM vendas) as valor_medio_vendas,
                (SELECT COUNT(*) FROM itens_venda) as total_itens_vendidos,
                (SELECT MIN(data) FROM vendas) as data_primeira_venda,
                (SELECT MAX(data) FROM vendas) as data_ultima_venda
            """;
        
        return jdbcTemplate.queryForMap(sql);
    }
    
    public List<Map<String, Object>> getVendasPorFaixaValor() {
        String sql = """
            SELECT 
                CASE 
                    WHEN total < 100 THEN 'Até R$ 100'
                    WHEN total < 500 THEN 'R$ 100 - R$ 500'
                    WHEN total < 1000 THEN 'R$ 500 - R$ 1000'
                    WHEN total < 5000 THEN 'R$ 1000 - R$ 5000'
                    ELSE 'Acima de R$ 5000'
                END as faixa_valor,
                COUNT(*) as total_vendas,
                COALESCE(SUM(total), 0) as valor_total
            FROM vendas
            GROUP BY 
                CASE 
                    WHEN total < 100 THEN 'Até R$ 100'
                    WHEN total < 500 THEN 'R$ 100 - R$ 500'
                    WHEN total < 1000 THEN 'R$ 500 - R$ 1000'
                    WHEN total < 5000 THEN 'R$ 1000 - R$ 5000'
                    ELSE 'Acima de R$ 5000'
                END
            ORDER BY MIN(total)
            """;
        
        return jdbcTemplate.queryForList(sql);
    }
    
    public List<Map<String, Object>> getClientesSemVendas() {
        String sql = """
            SELECT 
                c.codigo,
                c.nome,
                c.documento
            FROM clientes c
            LEFT JOIN vendas v ON c.id = v.id_cliente
            WHERE v.id IS NULL
            ORDER BY c.nome
            """;
        
        return jdbcTemplate.queryForList(sql);
    }
    
    public List<Map<String, Object>> getProdutosSemVendas() {
        String sql = """
            SELECT 
                p.codigo,
                p.descricao
            FROM produtos p
            LEFT JOIN itens_venda iv ON p.id = iv.id_produto
            WHERE iv.id IS NULL
            ORDER BY p.descricao
            """;
        
        return jdbcTemplate.queryForList(sql);
    }
}