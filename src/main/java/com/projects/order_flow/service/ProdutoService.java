package com.projects.order_flow.service;


import com.projects.order_flow.database.model.Categoria;
import com.projects.order_flow.database.model.Produto;
import com.projects.order_flow.database.repository.CategoriaRepository;
import com.projects.order_flow.database.repository.ProdutoRepository;
import com.projects.order_flow.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProdutoService {

    private ProdutoRepository produtoRepository;
    private CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto criarProduto(Produto produto) {
        Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId()).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public void deletarProduto(Long id){
        if (!produtoRepository.existsById(id)){
            throw new NotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
