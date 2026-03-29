package com.projects.order_flow.service;

import com.projects.order_flow.database.enums.Cargo;
import com.projects.order_flow.database.model.Usuario;
import com.projects.order_flow.database.repository.UsuarioRepository;
import com.projects.order_flow.dto.UsuarioRequestDTO;
import com.projects.order_flow.dto.UsuarioResponseDTO;
import com.projects.order_flow.exception.BusinessException;
import com.projects.order_flow.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private void validarCargo(Cargo cargo) {
        try {
            Cargo.valueOf(cargo.name().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BusinessException("Cargo inválido! Os cargos válidos são: ADMIN, GARCOM, COZINHA.");
        }
    }

    private UsuarioResponseDTO converterParaDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getCargo()
        );
    }


    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.nome());
        usuario.setCargo(usuarioDTO.cargo());
        usuario.setLogin(usuarioDTO.login());
        usuario.setSenha(usuarioDTO.senha());

        return converterParaDTO(usuarioRepository.save(usuario));
    }




    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public UsuarioResponseDTO obterUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário com esse ID não encontrado!"));
        return converterParaDTO(usuario);

    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO){
        Usuario usuarioAtualizado = usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário com esse ID não encontrado!"));

        validarCargo(usuarioRequestDTO.cargo());


        usuarioAtualizado.setSenha(usuarioRequestDTO.senha());
        usuarioAtualizado.setLogin(usuarioRequestDTO.login());
        usuarioAtualizado.setNome(usuarioRequestDTO.nome());
        usuarioAtualizado.setCargo(usuarioRequestDTO.cargo());

        usuarioRepository.save(usuarioAtualizado);

        return converterParaDTO(usuarioRepository.save(usuarioAtualizado));
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Não existe usuário com este ID!");
        }
        usuarioRepository.deleteById(id);
    }


}
