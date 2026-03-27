package com.projects.order_flow.dto;

import com.projects.order_flow.database.enums.FormaPagamento;
import com.projects.order_flow.database.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDTO(
        Long id,
        String comanda,
        Status status,
        LocalDateTime dataCriacao,
        BigDecimal valorTotal,
        String nomeAtendente,
        FormaPagamento formaPagamento
) {}