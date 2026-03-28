package com.projects.order_flow.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        BigDecimal preco,
        Long categoriaId,
        String descricao
) {
}
