package com.projects.order_flow.controller;


import com.projects.order_flow.service.PagamentoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;


    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> buscarTotal() {
        return ResponseEntity.ok(pagamentoService.calcularTotalVendas());
    }

    @GetMapping("/total-hoje")
    public ResponseEntity<BigDecimal> obterTotalVendasHoje() {
        BigDecimal total = pagamentoService.calcularVendasDeHoje();
        return ResponseEntity.ok(total);
    }






}
