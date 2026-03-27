package com.projects.order_flow.service;


import com.projects.order_flow.database.model.Pagamento;
import com.projects.order_flow.database.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public BigDecimal calcularTotalVendas() {
        BigDecimal total = pagamentoRepository.somarTodosOsPagamentos();
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal calcularVendasDeHoje() {

        LocalDateTime inicio = LocalDate.now().atStartOfDay();

        LocalDateTime fim = LocalDateTime.now();

        BigDecimal total = pagamentoRepository.somarVendasPorPeriodo(inicio, fim);
        return total != null ? total : BigDecimal.ZERO;
    }


}
