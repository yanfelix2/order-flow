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

    private Categoria buscarCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada com o ID: " + id));
    }

    private void preencherDadosProduto(Produto produto, ProdutoRequestDTO dto, Categoria categoria) {
        produto.setNome(dto.nome());
        produto.setPreco_atual(dto.preco());
        produto.setDescricao(dto.descricao());
        produto.setCategoria(categoria);
    }

    private ProdutoResponseDTO converterParaDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco_atual(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome(),
                produto.getDescricao()
        );
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequestDTO) {
        Categoria categoria = categoriaRepository.findById(produtoRequestDTO.categoriaId()).orElseThrow(() -> new NotFoundException("Categoria não encontrada com esse ID!"));
        Produto produto = new Produto();

        preencherDadosProduto(produto, produtoRequestDTO, categoria);

        return converterParaDTO(produtoRepository.save(produto));
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();

    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new NotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado com esse ID!"));
        Categoria categoria = buscarCategoria(produtoRequestDTO.categoriaId());

        preencherDadosProduto(produto, produtoRequestDTO, categoria);

        return converterParaDTO(produtoRepository.save(produto));

    }


}
