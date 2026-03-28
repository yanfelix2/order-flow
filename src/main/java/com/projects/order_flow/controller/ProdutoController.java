package com.projects.order_flow.controller;

import com.projects.order_flow.database.model.Produto;
import com.projects.order_flow.dto.ProdutoRequestDTO;
import com.projects.order_flow.dto.ProdutoResponseDTO;
import com.projects.order_flow.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody ProdutoRequestDTO produto){
        ProdutoResponseDTO novoProduto = produtoService.criarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos(){
        List<ProdutoResponseDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
       produtoService.deletarProduto(id);
       return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produto = produtoService.atualizarProduto(id, produtoRequestDTO);
        return ResponseEntity.ok(produto);
    }


}
