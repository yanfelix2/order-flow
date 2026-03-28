package com.projects.order_flow.service;


import com.projects.order_flow.database.model.Categoria;
import com.projects.order_flow.database.model.Produto;
import com.projects.order_flow.database.repository.CategoriaRepository;
import com.projects.order_flow.database.repository.ProdutoRepository;
import com.projects.order_flow.dto.ProdutoRequestDTO;
import com.projects.order_flow.dto.ProdutoResponseDTO;
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

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequestDTO) {
       Categoria categoria = categoriaRepository.findById(produtoRequestDTO.categoriaId()).orElseThrow(() -> new NotFoundException("Categoria não encontrada com esse ID!"));
       Produto produto = new Produto();
       produto.setNome(produtoRequestDTO.nome());
       produto.setPreco_atual(produtoRequestDTO.preco());
       produto.setCategoria(categoria);
       produto.setDescricao(produtoRequestDTO.descricao());

       produtoRepository.save(produto);

       return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco_atual(), produto.getCategoria().getId(), produto.getCategoria().getNome(), produto.getDescricao());
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(produto -> new ProdutoResponseDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco_atual(),
                        produto.getCategoria().getId(),
                        produto.getCategoria().getNome(),
                        produto.getDescricao()
                ))
                .toList();

    }

    public void deletarProduto(Long id){
        if (!produtoRepository.existsById(id)){
            throw new NotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO){
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado com esse ID!"));
        Categoria categoria = categoriaRepository.findById(produtoRequestDTO.categoriaId()).orElseThrow(() -> new NotFoundException("Categoria não encontrada com esse ID!"));



        produto.setNome(produtoRequestDTO.nome());
        produto.setPreco_atual(produtoRequestDTO.preco());
        produto.setDescricao(produtoRequestDTO.descricao());
        produto.setCategoria(categoria);

        produtoRepository.save(produto);

        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getPreco_atual(), produto.getCategoria().getId(), produto.getCategoria().getNome(), produto.getDescricao());


    }
}
