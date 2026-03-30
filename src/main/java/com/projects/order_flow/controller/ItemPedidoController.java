package com.projects.order_flow.controller;



import com.projects.order_flow.dto.ItemPedidoRequestDTO;
import com.projects.order_flow.dto.ItemPedidoResponseDTO;

import com.projects.order_flow.service.ItemPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<ItemPedidoResponseDTO> criarItemPedido(@RequestBody ItemPedidoRequestDTO itemPedido){
        ItemPedidoResponseDTO pedidoResponse = itemPedidoService.criarItemPedido(itemPedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItemPedido(@PathVariable Long id) {
        itemPedidoService.removerItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> atualizarItem(@PathVariable Long id, @RequestBody ItemPedidoRequestDTO itemPedidoRequestDTO){
        ItemPedidoResponseDTO itemPedidoResponseDTO = itemPedidoService.atualizarItem(id,itemPedidoRequestDTO);
        return ResponseEntity.ok(itemPedidoResponseDTO);
    }



}
