package com.projects.order_flow.service;


import com.projects.order_flow.database.enums.Status;
import com.projects.order_flow.database.model.Pagamento;
import com.projects.order_flow.database.model.Pedido;
import com.projects.order_flow.database.repository.PagamentoRepository;
import com.projects.order_flow.database.repository.PedidoRepository;
import com.projects.order_flow.database.repository.UsuarioRepository;
import com.projects.order_flow.dto.AtualizarStatusRequestDTO;
import com.projects.order_flow.dto.ItemPedidoResponseDTO;
import com.projects.order_flow.dto.PedidoRequestDTO;
import com.projects.order_flow.dto.PedidoResponseDTO;
import com.projects.order_flow.exception.BusinessException;
import com.projects.order_flow.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PagamentoRepository pagamentoRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, PagamentoRepository pagamentoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    private PedidoResponseDTO converterParaDTO(Pedido pedido) {
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getComanda(),
                pedido.getStatus(),
                pedido.getData_criacao(),
                pedido.getValor_total(),
                pedido.getUsuario().getNome(),
                pedido.getPagamento(),
                pedido.getItens().stream()
                        .map(item -> new ItemPedidoResponseDTO(
                                item.getId(),
                                item.getProduto().getId(),
                                item.getProduto().getNome(),
                                item.getQuantidade(),
                                item.getPreco_no_momento(),
                                item.getObservacao()
                        ))
                        .toList()
        );
    }

    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = new Pedido();
        pedido.setComanda(pedidoRequestDTO.comanda());
        pedido.setUsuario(usuarioRepository.findById(pedidoRequestDTO.usuarioId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        pedido.setStatus(Status.ABERTO);
        pedido.setData_criacao(LocalDateTime.now());
        pedido.setValor_total(BigDecimal.ZERO);

        pedidoRepository.save(pedido);

        return converterParaDTO(pedido);

    }

    public PedidoResponseDTO obterPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido com este ID não encontrado!"));

        return converterParaDTO(pedido);
    }

    public List<PedidoResponseDTO> listarTodos() {
       return pedidoRepository.findAll().stream()
               .map(this::converterParaDTO)
               .toList();
    }

    public void deletarPedido(Long id) {

        if (!pedidoRepository.existsById(id)) {
            throw new NotFoundException("Pedido não encontrado");
        }

        pedidoRepository.deleteById(id);
    }

    @Transactional
    public PedidoResponseDTO atualizarStatus(Long id, AtualizarStatusRequestDTO request) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));


        if (pedido.getStatus() == Status.PAGO || pedido.getStatus() == Status.CANCELADO) {
            throw new BusinessException("Não é possível alterar o status de um pedido finalizado!");
        }


        if (request.novoStatus() == Status.PAGO) {
            if (request.formaPagamento() == null) {
                throw new BusinessException("Para finalizar, informe a forma de pagamento!");
            }

            Pagamento novoPagamento = new Pagamento();
            novoPagamento.setPedido(pedido);
            novoPagamento.setValorPago(pedido.getValor_total());
            novoPagamento.setDataPagamento(LocalDateTime.now());
            novoPagamento.setFormaPagamento(request.formaPagamento());
            novoPagamento.setAtendente(pedido.getUsuario());

            pagamentoRepository.save(novoPagamento);
        }


        if (request.novoStatus() == Status.CANCELADO) {
            pedido.setValor_total(BigDecimal.ZERO);
            pedido.setPagamento(null);
        }


        pedido.setPagamento(request.formaPagamento());
        pedido.setStatus(request.novoStatus());

        pedidoRepository.save(pedido);


        return converterParaDTO(pedido);
    }

}
