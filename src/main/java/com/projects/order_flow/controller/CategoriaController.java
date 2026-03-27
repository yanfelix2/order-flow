package com.projects.order_flow.controller;


import com.projects.order_flow.database.model.Categoria;
import com.projects.order_flow.database.repository.CategoriaRepository;
import com.projects.order_flow.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria){
        Categoria novaCategoria = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id){
        try {
            categoriaService.deletarCategoria(id);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
}
