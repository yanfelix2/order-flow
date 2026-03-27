package com.projects.order_flow.dto;

public record ItemPedidoRequestDTO(
        Long pedidoId,
        Long produtoId,
        int quantidade,
        String observacao

) {
}
