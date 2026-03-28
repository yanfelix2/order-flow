package com.projects.order_flow.controller;


import com.projects.order_flow.database.model.Categoria;
import com.projects.order_flow.database.repository.CategoriaRepository;
import com.projects.order_flow.dto.CategoriaRequestDTO;
import com.projects.order_flow.dto.CategoriaResponseDTO;
import com.projects.order_flow.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@RequestBody CategoriaRequestDTO categoria){
        CategoriaResponseDTO novaCategoria = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id){
            categoriaService.deletarCategoria(id);
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias(){
        List<CategoriaResponseDTO> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        CategoriaResponseDTO categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaRequestDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }


}
