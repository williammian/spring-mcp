package br.com.wm.springmcp.tools;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import br.com.wm.springmcp.service.VendasAnalysisService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VendasMcpTools {
    
    private final VendasAnalysisService analysisService;
    
    @Tool(
        name = "get_resumo_geral",
        description = """
	        Obtém um resumo geral das vendas, clientes e produtos da empresa.
	        
	        Esta ferramenta fornece uma visão geral consolidada incluindo:
	        - Total de vendas (quantidade e valor)
	        - Número de clientes únicos
	        - Número de produtos cadastrados
	        - Período de análise (primeiro e último registro)
	        
	        Use esta ferramenta quando o usuário pedir um overview geral dos dados
	        ou quando precisar de contexto antes de análises mais específicas.
	        
	        Exemplo de resposta:
	        {
	            "total_clientes": 45,
	            "total_produtos": 120,
	            "total_vendas": 1250,
	            "valor_total_vendas": 89750.50,
	            "valor_medio_vendas": 71.80,
	            "total_itens_vendidos": 1500,
	            "data_primeira_venda": "2024-01-01",
	            "data_ultima_venda": "2024-12-31"
	        }
	        """
    )
    public Map<String, Object> getResumoGeral() {
        return analysisService.getResumoGeral();
    }
    
    @Tool(
        name = "get_vendas_por_periodo",
        description = """
	        Obtém o total de vendas e valores para um período específico.
	        
	        Esta ferramenta permite análise temporal das vendas entre duas datas.
	        Use quando o usuário quiser saber o desempenho em um período específico.
	        
	        Parâmetros:
	        - dataInicio: Data de início no formato YYYY-MM-DD (ex: 2024-01-01)
	        - dataFim: Data de fim no formato YYYY-MM-DD (ex: 2024-12-31)
	        
	        Retorna:
	        - total_vendas: Quantidade total de vendas no período
	        - valor_total: Valor total das vendas
	        - valor_medio: Valor médio por venda
	        - dias_periodo: Número de dias entre dataInicio e dataFim
	        
	        Dica: Para análises mensais, use sempre o primeiro e último dia do mês.
	        """
    )
    public Map<String, Object> getVendasPorPeriodo(
            String dataInicio, 
            String dataFim) {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        return analysisService.getTotalVendasPorPeriodo(inicio, fim);
    }
    
    @Tool(
        name = "get_vendas_por_mes",
        description = """
	        Obtém as vendas agrupadas por mês para um ano específico.
	        
	        Ideal para análise de tendências e sazonalidade ao longo do ano.
	        Os dados são ordenados cronologicamente do primeiro ao último mês.
	        
	        Parâmetro:
	        - ano: Ano para análise (ex: 2024)
	        
	        Retorna lista com:
	        - mes: Número do mês (1-12)
	        - total_vendas: Número de vendas no mês
	        - valor_total: Valor total das vendas do mês
	        
	        Use para identificar meses com melhor/pior performance ou padrões sazonais.
	        """
    )
    public List<Map<String, Object>> getVendasPorMes(int ano) {
        return analysisService.getVendasPorMes(ano);
    }
    
    @Tool(
        name = "get_top_clientes",
        description = """
	        Obtém os top clientes por valor de vendas (ranking decrescente).
	        
	        Identifica os clientes mais valiosos para a empresa com base no
	        valor total das vendas realizadas.
	        
	        Parâmetro:
	        - limite: Número máximo de clientes a retornar (ex: 5, 10, 20)
	        
	        Retorna lista ordenada por valor total decrescente:
	        - cliente_nome: Nome do cliente
	        - cliente_codigo: Código do cliente
	        - total_vendas: Número de vendas realizadas
	        - valor_total: Valor total das vendas
	        - valor_medio: Valor médio por venda do cliente
	        
	        Use para estratégias de fidelização, campanhas VIP ou análise de clientes-chave.
	        """
    )
    public List<Map<String, Object>> getTopClientes(int limite) {
        return analysisService.getTopClientes(limite);
    }
    
    @Tool(
        name = "get_top_produtos",
        description = """
	        Obtém os top produtos por valor de vendas (ranking decrescente).
	        
	        Identifica os produtos mais rentáveis e populares da empresa.
	        
	        Parâmetro:
	        - limite: Número máximo de produtos a retornar (ex: 5, 10, 20)
	        
	        Retorna lista ordenada por valor total decrescente:
	        - produto_descricao: Descrição do produto
	        - produto_codigo: Código do produto
	        - total_vendas: Número de vendas realizadas
	        - total_vendido: Quantidade total vendida
	        - valor_total: Valor total das vendas do produto
	        - valor_medio: Valor médio por venda do produto
	        
	        Use para análise de produtos estrela, estratégias de estoque ou promoções.
	        """
    )
    public List<Map<String, Object>> getTopProdutos(int limite) {
        return analysisService.getTopProdutos(limite);
    }
    
    @Tool(
        name = "get_vendas_por_dia",
        description = """
	        Obtém vendas diárias para um período específico (análise detalhada).
	        
	        Fornece dados granulares dia a dia, ideal para análise de padrões
	        diários, identificação de picos e análise de performance detalhada.
	        
	        Parâmetros:
	        - dataInicio: Data de início (YYYY-MM-DD)
	        - dataFim: Data de fim (YYYY-MM-DD)
	        
	        Retorna lista cronológica com:
	        - data: Data específica
	        - total_vendas: Número de vendas no dia
	        - valor_total: Valor total das vendas do dia
	        
	        Limite recomendado: máximo 90 dias para evitar sobrecarga de dados.
	        Use para análise de padrões semanais ou identificação de dias atípicos.
	        """
    )
    public List<Map<String, Object>> getVendasPorDia(
            String dataInicio, 
            String dataFim) {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        return analysisService.getVendasPorDia(inicio, fim);
    }
    
    @Tool(
        name = "get_vendas_por_faixa_valor",
        description = """
	        Obtém vendas agrupadas por faixa de valor (análise de distribuição).
	        
	        Analisa como as vendas se distribuem por diferentes faixas de valor,
	        útil para entender o perfil de compras dos clientes.
	        
	        Não requer parâmetros.
	        
	        Retorna lista com faixas pré-definidas:
	        - faixa_valor: Descrição da faixa (ex: "0-100", "100-500", "500-1000", "1000-5000", "5000+")
	        - total_vendas: Número de vendas na faixa
	        - valor_total: Valor total das vendas na faixa
	        
	        Use para análise de distribuição de vendas e estratégias de precificação.
	        """
    )
    public List<Map<String, Object>> getVendasPorFaixaValor() {
        return analysisService.getVendasPorFaixaValor();
    }
    
    @Tool(
        name = "get_clientes_sem_vendas",
        description = """
	        Obtém lista de clientes que não possuem vendas (clientes inativos).
	        
	        Identifica clientes cadastrados mas que nunca realizaram compras,
	        útil para campanhas de ativação ou limpeza de base.
	        
	        Não requer parâmetros.
	        
	        Retorna lista com:
	        - codigo: Código do cliente
	        - nome: Nome do cliente
	        - documento: Documento do cliente (CPF/CNPJ)
	        
	        Use para estratégias de reativação, campanhas de primeiro desconto
	        ou limpeza de base de clientes inativos.
	        """
    )
    public List<Map<String, Object>> getClientesSemVendas() {
        return analysisService.getClientesSemVendas();
    }
    
    @Tool(
        name = "get_produtos_sem_vendas",
        description = """
	        Obtém lista de produtos que não foram vendidos (produtos sem movimento).
	        
	        Identifica produtos cadastrados mas que nunca foram vendidos,
	        útil para análise de estoque morto ou revisão de catálogo.
	        
	        Não requer parâmetros.
	        
	        Retorna lista com:
	        - codigo: Código do produto
	        - descricao: Descrição do produto
	        
	        Use para análise de produtos sem movimento, estratégias de promoção
	        ou revisão do mix de produtos.
	        """
    )
    public List<Map<String, Object>> getProdutosSemVendas() {
        return analysisService.getProdutosSemVendas();
    }
}