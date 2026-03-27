package com.projects.order_flow.dto;

import com.projects.order_flow.database.enums.Cargo;

public record UsuarioRequestDTO(
        Cargo cargo,
        String login,
        String senha,
        String nome

) {

}
