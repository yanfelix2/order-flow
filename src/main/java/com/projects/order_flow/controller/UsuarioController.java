package com.projects.order_flow.controller;


import com.projects.order_flow.database.model.Usuario;
import com.projects.order_flow.dto.UsuarioRequestDTO;
import com.projects.order_flow.dto.UsuarioResponseDTO;
import com.projects.order_flow.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/criar")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) {
        UsuarioResponseDTO usuario = usuarioService.criarUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);

    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<UsuarioResponseDTO> listarUsuarioPorId(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.obterUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO){
        UsuarioResponseDTO usuarioAtualizado = usuarioService.atualizarUsuario(id,usuarioRequestDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }


}
