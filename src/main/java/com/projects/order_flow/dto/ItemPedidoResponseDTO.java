package com.projects.order_flow.dto;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long id,
        Long produtoId,
        String produtoNome,
        int quantidade,
        BigDecimal precoNoMomento,
        String observacao

) {
}
