package com.projects.order_flow.service;


import com.projects.order_flow.database.model.Categoria;
import com.projects.order_flow.database.repository.CategoriaRepository;
import com.projects.order_flow.dto.CategoriaRequestDTO;
import com.projects.order_flow.dto.CategoriaResponseDTO;
import com.projects.order_flow.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaDTO) {
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(categoriaDTO.nome());

        categoriaRepository.save(novaCategoria);

        return new CategoriaResponseDTO(novaCategoria.getId(), novaCategoria.getNome());

    }

    public void deletarCategoria(Long id){
        if (!categoriaRepository.existsById(id)){
            throw new NotFoundException("Não existe categoria com esse ID!");
        }
        categoriaRepository.deleteById(id);
    }

    public List<CategoriaResponseDTO> listarCategorias(){
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> new CategoriaResponseDTO(
                        categoria.getId(),
                        categoria.getNome()
                ))
                .toList();
    }

    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaRequestDTO categoriaRequestDTO){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new NotFoundException("Não existe categoria com este ID!"));
        categoria.setNome(categoriaRequestDTO.nome());

        categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(categoria.getId(), categoria.getNome());

    }

}

