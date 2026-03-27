package com.projects.order_flow.service;


import com.projects.order_flow.database.model.Categoria;
import com.projects.order_flow.database.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria criarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deletarCategoria(Long id){
        if (!categoriaRepository.existsById(id)){
            throw new RuntimeException("Não existe categoria com esse ID!");
        }
        categoriaRepository.deleteById(id);
    }
}
