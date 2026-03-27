package com.projects.order_flow.service;

import com.projects.order_flow.database.model.Usuario;
import com.projects.order_flow.database.repository.UsuarioRepository;
import com.projects.order_flow.dto.UsuarioRequestDTO;
import com.projects.order_flow.dto.UsuarioResponseDTO;
import com.projects.order_flow.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuario.getNome());
        usuario.setCargo(usuario.getCargo());
        usuario.setLogin(usuario.getLogin());
        usuario.setSenha(usuarioDTO.senha());

        usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(usuario.getId(), usuarioDTO.nome(), usuarioDTO.login(), usuarioDTO.cargo());
    }


    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getLogin(),
                        usuario.getCargo()
                ))
                .toList();
    }

    public UsuarioResponseDTO obterUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário com esse ID não encontrado!"));

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getCargo()
        );


    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Não existe usuário com este ID!");
        }
        usuarioRepository.deleteById(id);
    }


}
