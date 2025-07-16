-- Script para popular o banco de dados PostgreSQL
-- Execute este script após a criação das tabelas pela aplicação

-- Inserir produtos
INSERT INTO produtos (codigo, descricao) VALUES
('PROD001', 'Smartphone Samsung Galaxy S23'),
('PROD002', 'Notebook Dell Inspiron 15'),
('PROD003', 'Smart TV LG 55 4K'),
('PROD004', 'Tablet Apple iPad Air'),
('PROD005', 'Fone de Ouvido Sony WH-1000XM4'),
('PROD006', 'Smartwatch Apple Watch Series 8'),
('PROD007', 'Monitor LG UltraWide 29'),
('PROD008', 'Teclado Mecânico Logitech'),
('PROD009', 'Mouse Gamer Razer DeathAdder'),
('PROD010', 'Webcam Logitech C920'),
('PROD011', 'Impressora HP LaserJet Pro'),
('PROD012', 'Roteador Wi-Fi 6 TP-Link'),
('PROD013', 'SSD Samsung 1TB'),
('PROD014', 'Memória RAM DDR4 16GB'),
('PROD015', 'Placa de Vídeo RTX 4060'),
('PROD016', 'Processador Intel Core i7'),
('PROD017', 'Placa Mãe ASUS ROG'),
('PROD018', 'Fonte 650W 80+ Gold'),
('PROD019', 'Cooler CPU Corsair'),
('PROD020', 'Gabinete Gamer RGB');

-- Inserir clientes
INSERT INTO clientes (codigo, nome, documento) VALUES
('CLI001', 'João Silva Santos', '12345678901'),
('CLI002', 'Maria Oliveira Costa', '23456789012'),
('CLI003', 'Pedro Almeida Souza', '34567890123'),
('CLI004', 'Ana Carolina Ferreira', '45678901234'),
('CLI005', 'Carlos Eduardo Lima', '56789012345'),
('CLI006', 'Fernanda Ribeiro Dias', '67890123456'),
('CLI007', 'Roberto Carlos Pereira', '78901234567'),
('CLI008', 'Juliana Santos Rocha', '89012345678'),
('CLI009', 'Marcelo Henrique Gomes', '90123456789'),
('CLI010', 'Patrícia Fernandes Silva', '01234567890');

-- Inserir vendas (300 vendas)
-- Vamos criar um script mais eficiente usando uma função para gerar dados aleatórios

-- Função para gerar vendas aleatórias
DO $$
DECLARE
    i INTEGER := 1;
    cliente_id INTEGER;
    numero_venda INTEGER;
    data_venda DATE;
    total_venda DECIMAL(10,2);
    qtd_itens INTEGER;
    j INTEGER;
    produto_id INTEGER;
    seq_item INTEGER;
    qtde_item DECIMAL(10,3);
    preco_unit DECIMAL(10,2);
    total_item DECIMAL(10,2);
    venda_id INTEGER;
    
    -- Arrays com preços dos produtos
    precos DECIMAL[] := ARRAY[
        2999.99, 3499.99, 2799.99, 3299.99, 1299.99,
        2499.99, 1899.99, 899.99, 399.99, 799.99,
        1599.99, 699.99, 899.99, 799.99, 2899.99,
        1999.99, 1499.99, 599.99, 299.99, 799.99
    ];
    
BEGIN
    -- Inserir 300 vendas
    WHILE i <= 300 LOOP
        -- Selecionar cliente aleatório
        cliente_id := (SELECT id FROM clientes ORDER BY RANDOM() LIMIT 1);
        
        -- Número da venda sequencial
        numero_venda := i;
        
        -- Data aleatória nos últimos 365 dias
        data_venda := CURRENT_DATE - (RANDOM() * 365)::INTEGER;
        
        -- Quantidade de itens na venda (1 a 5)
        qtd_itens := (RANDOM() * 5)::INTEGER + 1;
        
        -- Calcular total da venda
        total_venda := 0;
        
        -- Inserir a venda
        INSERT INTO vendas (id_cliente, numero, data, total)
        VALUES (cliente_id, numero_venda, data_venda, 0)
        RETURNING id INTO venda_id;
        
        -- Inserir itens da venda
        j := 1;
        WHILE j <= qtd_itens LOOP
            -- Selecionar produto aleatório
            produto_id := (SELECT id FROM produtos ORDER BY RANDOM() LIMIT 1);
            
            seq_item := j;
            qtde_item := (RANDOM() * 10)::DECIMAL + 1; -- Quantidade entre 1 e 10
            preco_unit := precos[produto_id]; -- Preço do produto
            total_item := qtde_item * preco_unit;
            
            -- Inserir item da venda
            INSERT INTO itens_venda (id_venda, seq, id_produto, qtde, unit, total)
            VALUES (venda_id, seq_item, produto_id, qtde_item, preco_unit, total_item);
            
            -- Somar ao total da venda
            total_venda := total_venda + total_item;
            
            j := j + 1;
        END LOOP;
        
        -- Atualizar o total da venda
        UPDATE vendas SET total = total_venda WHERE id = venda_id;
        
        i := i + 1;
    END LOOP;
END $$;

-- Verificar os dados inseridos
SELECT 'Produtos inseridos: ' || COUNT(*) FROM produtos;
SELECT 'Clientes inseridos: ' || COUNT(*) FROM clientes;
SELECT 'Vendas inseridas: ' || COUNT(*) FROM vendas;
SELECT 'Itens de venda inseridos: ' || COUNT(*) FROM itens_venda;

-- Consultas de exemplo para verificar os dados
SELECT 
    v.numero,
    c.nome as cliente,
    v.data,
    v.total,
    COUNT(iv.id) as qtd_itens
FROM vendas v
JOIN clientes c ON v.id_cliente = c.id
LEFT JOIN itens_venda iv ON v.id = iv.id_venda
GROUP BY v.id, v.numero, c.nome, v.data, v.total
ORDER BY v.numero
LIMIT 10;

-- Top 10 produtos mais vendidos
SELECT 
    p.codigo,
    p.descricao,
    SUM(iv.qtde) as total_vendido,
    COUNT(iv.id) as qtd_transacoes
FROM produtos p
JOIN itens_venda iv ON p.id = iv.id_produto
GROUP BY p.id, p.codigo, p.descricao
ORDER BY total_vendido DESC
LIMIT 10;

-- Vendas por cliente
SELECT 
    c.codigo,
    c.nome,
    COUNT(v.id) as qtd_vendas,
    SUM(v.total) as total_compras
FROM clientes c
LEFT JOIN vendas v ON c.id = v.id_cliente
GROUP BY c.id, c.codigo, c.nome
ORDER BY total_compras DESC;