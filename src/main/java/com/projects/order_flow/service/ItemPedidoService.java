package com.projects.order_flow.service;

import com.projects.order_flow.database.enums.Status;
import com.projects.order_flow.database.model.ItemPedido;
import com.projects.order_flow.database.repository.ItemPedidoRepository;
import com.projects.order_flow.database.repository.PedidoRepository;
import com.projects.order_flow.database.repository.ProdutoRepository;
import com.projects.order_flow.dto.ItemPedidoRequestDTO;
import com.projects.order_flow.dto.ItemPedidoResponseDTO;
import com.projects.order_flow.exception.BusinessException;
import com.projects.order_flow.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }


    @Transactional
    public ItemPedidoResponseDTO criarItemPedido(ItemPedidoRequestDTO itemPedidoRequest) {



        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedidoRepository.findById(itemPedidoRequest.pedidoId()).orElseThrow(() -> new NotFoundException("Pedido não encontrado")));

        if (itemPedido.getPedido().getStatus() == Status.PAGO){
            throw new BusinessException("Este pedido já foi pago, Abra uma nova comanda!");
        }

        if (itemPedido.getPedido().getStatus() == Status.CANCELADO) {
            throw new BusinessException("Não é possível adicionar itens a um pedido cancelado!");
        }

        if (itemPedido.getPedido().getStatus() == Status.ABERTO) {
            itemPedido.getPedido().setStatus(Status.PREPARANDO);
        }




        itemPedido.setProduto(produtoRepository.findById(itemPedidoRequest.produtoId()).orElseThrow(() -> new RuntimeException("Produto não encontrado")));
        itemPedido.setQuantidade(itemPedidoRequest.quantidade());
        itemPedido.setObservacao(itemPedidoRequest.observacao());




        BigDecimal valorTotal = itemPedido.getPedido().getValor_total() == null ? BigDecimal.ZERO : itemPedido.getPedido().getValor_total();
        itemPedido.setPreco_no_momento(itemPedido.getProduto().getPreco_atual());

        var multiplicacao =  itemPedido.getPreco_no_momento().multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));

        BigDecimal novoTotalPedido = valorTotal.add(multiplicacao);

        itemPedido.getPedido().setValor_total(novoTotalPedido);

        itemPedidoRepository.save(itemPedido);


        return new ItemPedidoResponseDTO(itemPedido.getId(), itemPedido.getProduto().getNome(), itemPedido.getQuantidade(), itemPedido.getPreco_no_momento(), itemPedido.getObservacao());
    }


}
