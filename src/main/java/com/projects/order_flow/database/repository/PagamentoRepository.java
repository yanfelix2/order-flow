package com.projects.order_flow.database.repository;


import com.projects.order_flow.database.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    @Query("SELECT SUM(p.valorPago) FROM Pagamento p")
    BigDecimal somarTodosOsPagamentos();
    @Query("SELECT SUM(p.valorPago) FROM Pagamento p WHERE p.dataPagamento BETWEEN :inicio AND :fim")
    BigDecimal somarVendasPorPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);



}
