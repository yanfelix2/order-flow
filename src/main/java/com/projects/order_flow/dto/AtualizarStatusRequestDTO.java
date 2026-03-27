package com.projects.order_flow.dto;

import com.projects.order_flow.database.enums.FormaPagamento;
import com.projects.order_flow.database.enums.Status;

public record AtualizarStatusRequestDTO(
        Status novoStatus,
        FormaPagamento formaPagamento,
        Long atendenteId
) {
}
