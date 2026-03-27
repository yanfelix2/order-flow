package com.projects.order_flow.dto;

import com.projects.order_flow.database.enums.Cargo;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String login,
        Cargo cargo

) {
}
