package com.projects.order_flow.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO (
        Long id,
        String nome,
        BigDecimal preco,
        Long categoriaId,
        String categoriaNome,
        String descricao
) {
}
