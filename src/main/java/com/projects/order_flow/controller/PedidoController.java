package com.projects.order_flow.controller;


import com.projects.order_flow.dto.AtualizarStatusRequestDTO;
import com.projects.order_flow.dto.PedidoRequestDTO;
import com.projects.order_flow.dto.PedidoResponseDTO;
import com.projects.order_flow.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO pedido){
        PedidoResponseDTO pedidoResponseDTO = pedidoService.criarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponseDTO);

    }



    @GetMapping("/listar")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(){
        List<PedidoResponseDTO> lista = pedidoService.listarTodos();

        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id){

            pedidoService.deletarPedido(id);
            return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequestDTO novoStatus){
       PedidoResponseDTO pedidoResponseDTO = pedidoService.atualizarStatus(id,novoStatus);
        return ResponseEntity.ok(pedidoResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obterPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO pedidoResponseDTO = pedidoService.obterPedidoPorId(id);
        return ResponseEntity.ok(pedidoResponseDTO);
    }


}
